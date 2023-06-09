package be.howest.jarivalentine.virtualcloset.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("SELECT * FROM item WHERE type = :type OR :type IS NULL")
    fun getAllItems(type: String?): Flow<List<Item>>

    @Query("UPDATE item SET available = NOT available WHERE id = :id")
    suspend fun toggleAvailable(id: Int)

    @Query("SELECT COUNT(*) FROM item WHERE available = 0")
    suspend fun hasUnavailableItems(): Boolean

    @Query("SELECT COUNT(*) FROM item JOIN outfit_item ON item.id = outfit_item.itemId WHERE outfit_item.outfitId = :id AND item.available = 0")
    suspend fun hasUnavailableItems(id: Int): Boolean
}