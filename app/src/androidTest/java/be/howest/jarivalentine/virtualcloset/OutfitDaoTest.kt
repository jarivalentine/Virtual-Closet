package be.howest.jarivalentine.virtualcloset

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import be.howest.jarivalentine.virtualcloset.data.Item
import be.howest.jarivalentine.virtualcloset.data.Outfit
import be.howest.jarivalentine.virtualcloset.data.OutfitDao
import be.howest.jarivalentine.virtualcloset.data.OutfitItem
import be.howest.jarivalentine.virtualcloset.data.VirtualClosetDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class OutfitDaoTest {

    private lateinit var outfitDao: OutfitDao
    private lateinit var virtualClosetDatabase: VirtualClosetDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        virtualClosetDatabase = Room.inMemoryDatabaseBuilder(context, VirtualClosetDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        outfitDao = virtualClosetDatabase.outfitDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        virtualClosetDatabase.close()
    }

    private var outfit1 = Outfit(1, "Outfit 1", "Summer")
    private var outfit2 = Outfit(2, "Outfit 2", "Winter")
    private var item1 = Item(1, "Blue jacket", "Tops", "H&M", "https://i.imgur.com/pVOsyRO.png", true)
    private var item2 = Item(2, "Black jeans", "Bottoms", "H&M", "https://i.imgur.com/pVOsyRO.png", true)
    private var item3 = Item(3, "Red shoes", "Shoes", "Adidas", "https://i.imgur.com/OpIriXg.png", true)
    private var item4 = Item(4, "White shoes", "Shoes", "Adidas", "https://i.imgur.com/OpIriXg.png", true)

    private suspend fun addOneOutfitToDb() {
        outfitDao.insert(outfit1)
        outfitDao.insertOutfitItems(listOf(
            OutfitItem(outfit1.id, item1.id),
            OutfitItem(outfit1.id, item2.id),
            OutfitItem(outfit1.id, item3.id)
        ))
    }

    private suspend fun addTwoOutfitsToDb() {
        outfitDao.insert(outfit1)
        outfitDao.insertOutfitItems(listOf(
            OutfitItem(outfit1.id, item1.id),
            OutfitItem(outfit1.id, item2.id),
            OutfitItem(outfit1.id, item3.id)
        ))
        outfitDao.insert(outfit2)
        outfitDao.insertOutfitItems(listOf(
            OutfitItem(outfit2.id, item1.id),
            OutfitItem(outfit2.id, item2.id),
            OutfitItem(outfit2.id, item4.id)
        ))
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsOutfitIntoDB() = runBlocking {
        addOneOutfitToDb()
        val allOutfits = outfitDao.getAllOutfits(null).first()
        assert(allOutfits[0] == outfit1)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertAllItems_returnsAllItems() = runBlocking {
        addTwoOutfitsToDb()
        val allOutfits = outfitDao.getAllOutfits(null).first()
        assert(allOutfits[0] == outfit1)
        assert(allOutfits[1] == outfit2)
    }

    @Test
    @Throws(Exception::class)
    fun doaDelete_deletesOutfitFromDB() = runBlocking {
        addOneOutfitToDb()
        outfitDao.delete(outfit1.id)
        val allOutfits = outfitDao.getAllOutfits(null).first()
        assert(allOutfits.isEmpty())
    }
}