package be.howest.jarivalentine.virtualcloset.data

import androidx.room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Dao
interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(outfit: Outfit): Long

    @Delete
    suspend fun delete(outfit: Outfit)

    @Query("SELECT * FROM outfit WHERE name LIKE :query")
    fun getAllOutfits(query: String): Flow<List<Outfit>>

    suspend fun insertOutfitItems(outfitItems: List<OutfitItem>) {
        withContext(Dispatchers.IO) {
            insertOutfitItemsInternal(outfitItems)
        }
    }

    @Insert
    suspend fun insertOutfitItemsInternal(outfitItems: List<OutfitItem>)
}
