package be.howest.jarivalentine.virtualcloset.data.item

import be.howest.jarivalentine.virtualcloset.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllItemsStream(type: String?): Flow<List<Item>>

    fun getItemStream(id: Int): Flow<Item?>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(id: Int)

    suspend fun toggleAvailable(id: Int)

    suspend fun hasUnavailableItems(): Boolean
    suspend fun hasUnavailableItems(id: Int): Boolean
}
