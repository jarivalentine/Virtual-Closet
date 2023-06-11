package be.howest.jarivalentine.virtualcloset.ui.navigation

import androidx.annotation.StringRes
import be.howest.jarivalentine.virtualcloset.R

enum class NavigationDestination(@StringRes val title: Int) {
    Item(title = R.string.item_title),
    CreateItem(title = R.string.create_item_title),
    CreateOutfit(title = R.string.create_outfit_title),
    Outfit(title = R.string.outfit_title),
    Profile(title = R.string.profile_title)
}