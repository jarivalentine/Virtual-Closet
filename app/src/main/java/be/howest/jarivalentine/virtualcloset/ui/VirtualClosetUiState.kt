package be.howest.jarivalentine.virtualcloset.ui

import be.howest.jarivalentine.virtualcloset.model.Brand
import be.howest.jarivalentine.virtualcloset.model.Item
import be.howest.jarivalentine.virtualcloset.model.Outfit

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
    val imageUri: String = "",
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
    imageUri = imageUri,
    available = available
)

data class OutfitUiState(
    val id: Int = 0,
    val name: String = "",
    val season: String = "",
    val label: String = "",
    val imageUri: String = "",
    val actionEnabled: Boolean = false
)

fun OutfitUiState.isValid() : Boolean {
    return name.isNotBlank() && season.isNotBlank()
}

fun OutfitUiState.toOutfit(): Outfit = Outfit(
    id = id,
    name = name,
    season = season,
    label = label,
    imageUri = imageUri
)
