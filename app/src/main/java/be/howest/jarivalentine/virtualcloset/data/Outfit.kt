package be.howest.jarivalentine.virtualcloset.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import be.howest.jarivalentine.virtualcloset.R

data class Outfit(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
)

val outfits = listOf(
    Outfit(R.drawable.outfit1, R.string.outfit1),
    Outfit(R.drawable.outfit2, R.string.outfit2)
)

val labels = listOf(
    "Summer", "Winter", "Autumn", "Spring"
)
