package be.howest.jarivalentine.virtualcloset.data.item

import be.howest.jarivalentine.virtualcloset.model.Item

class OfflineItemRepository(private val itemDao: ItemDao) : ItemRepository {

        override fun getAllItemsStream(type: String?) = itemDao.getAllItems(type)

        override fun getItemStream(id: Int) = itemDao.getItem(id)

        override suspend fun insertItem(item: Item) = itemDao.insert(item)

        override suspend fun deleteItem(id: Int) = itemDao.delete(id)

        override suspend fun toggleAvailable(id: Int) = itemDao.toggleAvailable(id)

        override suspend fun hasUnavailableItems() = itemDao.hasUnavailableItems()

        override suspend fun hasUnavailableItems(id: Int) = itemDao.hasUnavailableItems(id)
}