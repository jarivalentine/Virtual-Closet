package be.howest.jarivalentine.virtualcloset.ui

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