package be.howest.jarivalentine.virtualcloset.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.howest.jarivalentine.virtualcloset.VirtualClosetApplication
import be.howest.jarivalentine.virtualcloset.data.ItemRepository
import be.howest.jarivalentine.virtualcloset.data.OutfitRepository
import kotlinx.coroutines.flow.*

class VirtualClosetViewModel(
    private val itemRepository: ItemRepository,
    private val outfitRepository: OutfitRepository
) : ViewModel() {

    // Item Screen

    val virtualClosetUiState: StateFlow<VirtualClosetUiState> = combine(
        itemRepository.getAllItemsStream(type = null),
        outfitRepository.getAllOutfitsStream(query = "")
    ) { items, outfits ->
        VirtualClosetUiState(itemList = items, outfitList = outfits)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = VirtualClosetUiState()
    )

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

    fun createOutfit() {
        TODO("Not yet implemented")
    }

    suspend fun toggleAvailable() {
        _selectedItems.value.forEach { itemRepository.toggleAvailable(it) }
        _selectedItems.value = emptyList()
        _selecting.value = false
    }

    // Create screen

    var itemUiState by mutableStateOf(ItemUiState())
        private set


    fun updateItemUiState(item: ItemUiState) {
        itemUiState = item.copy(actionEnabled = item.isValid());
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
                VirtualClosetViewModel(itemRepository, outfitRepository)
            }
        }
    }
}