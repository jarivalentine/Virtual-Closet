package be.howest.jarivalentine.virtualcloset.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(outfit: Outfit)

    @Delete
    suspend fun delete(outfit: Outfit)

    @Query("SELECT * FROM outfit WHERE name LIKE :query")
    fun getAllOutfits(query: String): Flow<List<Outfit>>
}
