package be.howest.jarivalentine.virtualcloset.ui

import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.Outfit

data class VirtualClosetUiState(
    val itemList: List<Item> = listOf(),
    val outfitList: List<Outfit> = listOf()
)

data class ItemUiState(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val available: Boolean = true,
    val actionEnabled: Boolean = false
)

fun ItemUiState.isValid() : Boolean {
    return name.isNotBlank() && type.isNotBlank()
}

fun ItemUiState.toItem(): Item = Item(
    id = id,
    name = name,
    type = type,
    available = available
)

data class OutfitUiState(
    val id: Int = 0,
    val name: String = "",
    val label: String = "",
    val actionEnabled: Boolean = false
)

fun OutfitUiState.isValid() : Boolean {
    return name.isNotBlank() && label.isNotBlank()
}

fun OutfitUiState.toOutfit(): Outfit = Outfit(
    id = id,
    name = name,
    label = label
)
