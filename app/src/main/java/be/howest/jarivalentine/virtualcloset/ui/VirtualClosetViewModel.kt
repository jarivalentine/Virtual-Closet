package be.howest.jarivalentine.virtualcloset.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.jarivalentine.virtualcloset.VirtualClosetApplication
import be.howest.jarivalentine.virtualcloset.data.BrandRepository
import be.howest.jarivalentine.virtualcloset.data.ItemRepository
import be.howest.jarivalentine.virtualcloset.data.OutfitRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class VirtualClosetViewModel(
    private val itemRepository: ItemRepository,
    private val outfitRepository: OutfitRepository,
    private val brandRepository: BrandRepository
) : ViewModel() {

    // Items and outfits
    val virtualClosetUiState: StateFlow<VirtualClosetUiState>
        get() = _virtualClosetUiState

    private val _virtualClosetUiState = MutableStateFlow(VirtualClosetUiState())

    var brandUiState: BrandUiState by mutableStateOf(BrandUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            combine(
                itemRepository.getAllItemsStream(type = null),
                outfitRepository.getAllOutfitsStream(query = null)
            ) { items, outfits ->
                VirtualClosetUiState(itemList = items, outfitList = outfits)
            }.collect { state ->
                _virtualClosetUiState.value = state
            }
        }

        viewModelScope.launch {
            brandUiState = try {
                val result = brandRepository.getBrands()
                BrandUiState.Success(result)
            } catch (e: IOException) {
                BrandUiState.Error
            } catch (e: HttpException) {
                BrandUiState.Error
            }
        }
    }

    fun searchOutfits(query: String) {
        viewModelScope.launch {
            val outfits = outfitRepository.getAllOutfitsStream(query = "%$query%").first()
            val currentState = _virtualClosetUiState.value
            val updatedState = currentState.copy(outfitList = outfits)
            _virtualClosetUiState.value = updatedState
        }
    }

    fun filterItems(tag: String) {
        viewModelScope.launch {
            val type = if (tag == "All") null else tag
            val items = itemRepository.getAllItemsStream(type = type).first()
            val currentState = _virtualClosetUiState.value
            val updatedState = currentState.copy(itemList = items)
            _virtualClosetUiState.value = updatedState
        }
    }

    private val _selectedItems = mutableStateOf<List<Int>>(emptyList())
    val selectedItems: State<List<Int>> = _selectedItems

    private val _selecting = mutableStateOf(false)
    val selecting: State<Boolean> = _selecting

    fun toggleSelect(id: Int) {
        if (!selecting.value) _selecting.value = true
        val updatedSelection = if (_selectedItems.value.contains(id)) {
            _selectedItems.value - id
        } else {
            _selectedItems.value + id
        }
        if (updatedSelection.isEmpty()) _selecting.value = false
        _selectedItems.value = updatedSelection
    }

    suspend fun deleteSelected() {
        _selectedItems.value.forEach { itemRepository.deleteItem(it) }
        _selectedItems.value = emptyList()
        _selecting.value = false
    }

    suspend fun toggleAvailable() {
        _selectedItems.value.forEach { itemRepository.toggleAvailable(it) }
        _selectedItems.value = emptyList()
        _selecting.value = false
    }

    // Create outfit

    var outfitUiState by mutableStateOf(OutfitUiState())
        private set

    fun updateOutfitUiState(outfit: OutfitUiState) {
        outfitUiState = outfit.copy(actionEnabled = outfit.isValid())
    }

    suspend fun saveOutfit(context: Context) {
        labelImage(outfitUiState.imageUri, context)
        val newOutfit = outfitUiState.toOutfit()
        val selectedItems = _selectedItems.value
        outfitRepository.insertOutfitWithItems(newOutfit, selectedItems)
        _selectedItems.value = emptyList()
        _selecting.value = false
        outfitUiState = OutfitUiState()
    }

    private fun labelImage(imageUri: String, context: Context) {
        val image: InputImage
        val types = listOf(
            "Shorts", "Blazer", "Denim", "Fur", "Knitting", "Swimwear", "Dress"
        )
        val matches = mutableListOf<Pair<String, Float>>()
        try {
            image = InputImage.fromFilePath(context, imageUri.toUri())
            val labelerOptions = ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.5f)
                .build()
            val labeler = ImageLabeling.getClient(labelerOptions)


            labeler.process(image)
                .addOnSuccessListener { labels ->
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        if (types.contains(text)) {
                            matches.add(Pair(text, confidence))
                        }
                    }
                    val bestMatch = matches.maxByOrNull { it.second }
                    updateOutfitUiState(
                        outfitUiState.copy(
                            label = bestMatch?.first ?: "",
                        )
                    )
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    suspend fun hasUnavailableItems(id: Int): Boolean {
        return itemRepository.hasUnavailableItems(id)
    }

    suspend fun deleteOutfit(id: Int) {
        outfitRepository.deleteOutfit(id)
    }

    // Create item

    var itemUiState by mutableStateOf(ItemUiState())
        private set


    fun updateItemUiState(item: ItemUiState) {
        Log.d("VirtualClosetViewModel", item.toString())
        itemUiState = item.copy(actionEnabled = item.isValid())
    }

    suspend fun saveItem() {
        itemRepository.insertItem(itemUiState.toItem())
        itemUiState = ItemUiState()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VirtualClosetApplication)
                val itemRepository = application.container.itemRepository
                val outfitRepository = application.container.outfitRepository
                val brandRepository = application.container.brandRepository
                VirtualClosetViewModel(itemRepository, outfitRepository, brandRepository)
            }
        }
    }
}