package be.howest.jarivalentine.virtualcloset.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VirtualClosetViewModel {

    private val _uiState = MutableStateFlow(VirtualClosetUiState())
    val uiState: StateFlow<VirtualClosetUiState> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update {
            it.copy(name = name)
        }
    }
}