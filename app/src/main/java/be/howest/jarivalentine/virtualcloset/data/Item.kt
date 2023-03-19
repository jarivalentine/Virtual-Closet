package be.howest.jarivalentine.virtualcloset.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import be.howest.jarivalentine.virtualcloset.R

data class Item(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
)

val items = listOf(
    Item(R.drawable.jeans1, R.string.jeans1)
)
