package be.howest.jarivalentine.virtualcloset.data.outfit

import be.howest.jarivalentine.virtualcloset.model.Outfit
import kotlinx.coroutines.flow.Flow

interface OutfitRepository {
    fun getAllOutfitsStream(query: String?): Flow<List<Outfit>>

    suspend fun insertOutfit(outfit: Outfit): Long

    suspend fun deleteOutfit(id: Int)

    suspend fun insertOutfitWithItems(outfit: Outfit, items: List<Int>)
}
