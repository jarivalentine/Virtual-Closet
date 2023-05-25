package be.howest.jarivalentine.virtualcloset.data

class OfflineOutfitRepository(private val outfitDao: OutfitDao) : OutfitRepository {

    override fun getAllOutfitsStream(query: String) = outfitDao.getAllOutfits(query)

    override suspend fun insertOutfit(outfit: Outfit) = outfitDao.insert(outfit)

    override suspend fun deleteItem(outfit: Outfit) = outfitDao.delete(outfit)
}
