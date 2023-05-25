package be.howest.jarivalentine.virtualcloset.ui

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

class VirtualClosetViewModel(itemRepository: ItemRepository, outfitRepository: OutfitRepository) : ViewModel() {

    val virtualClosetUiState: StateFlow<VirtualClosetUiState> = combine(
        itemRepository.getAllItemsStream(type = ""),
        outfitRepository.getAllOutfitsStream(query = "")
    ) { items, outfits ->
        VirtualClosetUiState(itemList = items, outfitList = outfits)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = VirtualClosetUiState()
    )

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