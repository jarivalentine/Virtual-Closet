package be.howest.jarivalentine.virtualcloset.data

import android.content.Context

interface AppContainer {
    val itemRepository: ItemRepository
    val outfitRepository: OutfitRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val itemRepository: ItemRepository by lazy {
        OfflineItemRepository(VirtualClosetDatabase.getDatabase(context).itemDao())
    }

    override val outfitRepository: OutfitRepository by lazy {
        OfflineOutfitRepository(VirtualClosetDatabase.getDatabase(context).outfitDao())
    }
}
