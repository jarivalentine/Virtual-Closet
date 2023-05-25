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
}
