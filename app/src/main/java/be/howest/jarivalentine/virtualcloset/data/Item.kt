package be.howest.jarivalentine.virtualcloset.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import be.howest.jarivalentine.virtualcloset.R

data class Item(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
)

val items = listOf(
    Item(R.drawable.jeans1, R.string.jeans1),
    Item(R.drawable.shoes1, R.string.shoes1),
    Item(R.drawable.jacket1, R.string.jacket1),
    Item(R.drawable.hoodie1, R.string.hoodie1),
    Item(R.drawable.jeans1, R.string.jeans1),
    Item(R.drawable.jeans1, R.string.jeans1),
    Item(R.drawable.jeans1, R.string.jeans1)
)

val tags = listOf(
    "Shorts", "Pants", "Shoes", "T-Shirt", "Hoodie", "Sweater", "Jacket"
)

val outfits = listOf(
    "Summer fit"
)
