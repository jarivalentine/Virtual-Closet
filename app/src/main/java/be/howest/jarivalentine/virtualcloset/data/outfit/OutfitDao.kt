package be.howest.jarivalentine.virtualcloset.data.outfit

import androidx.room.*
import be.howest.jarivalentine.virtualcloset.model.Outfit
import be.howest.jarivalentine.virtualcloset.model.OutfitItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Dao
interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(outfit: Outfit): Long

    @Query("DELETE FROM outfit WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM outfit WHERE name LIKE :query OR label LIKE :query OR season LIKE :query OR :query IS NULL")
    fun getAllOutfits(query: String?): Flow<List<Outfit>>

    suspend fun insertOutfitItems(outfitItems: List<OutfitItem>) {
        withContext(Dispatchers.IO) {
            insertOutfitItemsInternal(outfitItems)
        }
    }

    @Insert
    suspend fun insertOutfitItemsInternal(outfitItems: List<OutfitItem>)
}
