package be.howest.jarivalentine.virtualcloset.data

import kotlinx.coroutines.flow.Flow

interface OutfitRepository {
    fun getAllOutfitsStream(query: String): Flow<List<Outfit>>

    suspend fun insertOutfit(outfit: Outfit)

    suspend fun deleteItem(outfit: Outfit)
}
