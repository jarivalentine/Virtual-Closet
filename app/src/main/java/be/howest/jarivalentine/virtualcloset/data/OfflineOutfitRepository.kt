package be.howest.jarivalentine.virtualcloset.data

class OfflineOutfitRepository(private val outfitDao: OutfitDao) : OutfitRepository {

    override fun getAllOutfitsStream(query: String?) = outfitDao.getAllOutfits(query)

    override suspend fun insertOutfit(outfit: Outfit) = outfitDao.insert(outfit)

    override suspend fun deleteOutfit(id: Int) = outfitDao.delete(id)

    override suspend fun insertOutfitWithItems(outfit: Outfit, items: List<Int>) {
        val outfitId = outfitDao.insert(outfit)
        val outfitItems = items.map { itemId -> OutfitItem(outfitId.toInt(), itemId) }
        outfitDao.insertOutfitItems(outfitItems)
    }
}
