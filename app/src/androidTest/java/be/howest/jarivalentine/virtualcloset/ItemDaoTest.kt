package be.howest.jarivalentine.virtualcloset

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.howest.jarivalentine.virtualcloset.model.Item
import be.howest.jarivalentine.virtualcloset.data.item.ItemDao
import be.howest.jarivalentine.virtualcloset.data.VirtualClosetDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao
    private lateinit var virtualClosetDatabase: VirtualClosetDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        virtualClosetDatabase = Room.inMemoryDatabaseBuilder(context, VirtualClosetDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = virtualClosetDatabase.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        virtualClosetDatabase.close()
    }

    private var item1 = Item(1, "Blue jacket", "Tops", "H&M", "https://i.imgur.com/pVOsyRO.png", true, "")
    private var item2 = Item(2, "Black jeans", "Bottoms", "H&M", "https://i.imgur.com/pVOsyRO.png", true, "")

    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = itemDao.getAllItems(null).first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems(null).first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDBByCategory() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems("Bottom").first()
        assertEquals(allItems[0], item2)
    }

    @Test
    @Throws(Exception::class)
    fun doaGetItem_returnsItemFromDB() = runBlocking {
        addTwoItemsToDb()
        val item = itemDao.getItem(1).first()
        assertEquals(item, item1)
    }

    @Test
    @Throws(Exception::class)
    fun doaDelete_deletesItemFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.delete(1)
        val allItems = itemDao.getAllItems(null).first()
        assertEquals(allItems[0], item2)
    }

    @Test
    @Throws(Exception::class)
    fun doaToggleAvailable_togglesAvailableFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.toggleAvailable(1)
        val item = itemDao.getItem(1).first()
        assertEquals(item.available, false)
    }

    @Test
    @Throws(Exception::class)
    fun doaHasUnavailableItems_returnsTrueIfUnavailableItemsInDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.toggleAvailable(1)
        val hasUnavailableItems = itemDao.hasUnavailableItems()
        assertEquals(hasUnavailableItems, true)
    }

    @Test
    @Throws(Exception::class)
    fun doaHasUnavailableItems_returnsFalseIfNoUnavailableItemsInDB() = runBlocking {
        addTwoItemsToDb()
        val hasUnavailableItems = itemDao.hasUnavailableItems()
        assertEquals(hasUnavailableItems, false)
    }
}
