package be.howest.jarivalentine.virtualcloset.ui

import be.howest.jarivalentine.virtualcloset.data.Brand
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.Outfit

data class VirtualClosetUiState(
    val itemList: List<Item> = listOf(),
    val outfitList: List<Outfit> = listOf(),
)

sealed interface BrandUiState {
    data class Success(val brands: List<Brand>) : BrandUiState
    object Error : BrandUiState
    object Loading : BrandUiState
}

data class ItemUiState(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val brand: String = "",
    val brandImage: String = "",
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
    brand = brand,
    brandImage = brandImage,
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
