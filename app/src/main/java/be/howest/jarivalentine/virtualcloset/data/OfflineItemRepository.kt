package be.howest.jarivalentine.virtualcloset.data

class OfflineItemRepository(private val itemDao: ItemDao) : ItemRepository {

        override fun getAllItemsStream(type: String?) = itemDao.getAllItems(type)

        override fun getItemStream(id: Int) = itemDao.getItem(id)

        override suspend fun insertItem(item: Item) = itemDao.insert(item)

        override suspend fun deleteItem(item: Item) = itemDao.delete(item)
}