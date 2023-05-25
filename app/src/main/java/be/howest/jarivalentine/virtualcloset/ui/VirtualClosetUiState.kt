package be.howest.jarivalentine.virtualcloset.ui

import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.Outfit

data class VirtualClosetUiState(
    val itemList: List<Item> = listOf(),
    val outfitList: List<Outfit> = listOf(),
)