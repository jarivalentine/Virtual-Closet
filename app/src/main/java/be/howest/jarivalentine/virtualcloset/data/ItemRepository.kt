package be.howest.jarivalentine.virtualcloset.data;

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllItemsStream(type: String?): Flow<List<Item>>

    fun getItemStream(id: Int): Flow<Item?>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(id: Int)

    suspend fun toggleAvailable(id: Int)

    suspend fun hasUnavailableItems(): Boolean
}
