package be.howest.jarivalentine.virtualcloset.data.brand

import be.howest.jarivalentine.virtualcloset.data.brand.BrandRepository
import be.howest.jarivalentine.virtualcloset.model.Brand
import be.howest.jarivalentine.virtualcloset.network.BrandApiService

class NetworkBrandRepository(
    private val brandApiService: BrandApiService
) : BrandRepository {
    override suspend fun getBrands(): List<Brand> {
        val res = brandApiService.getBrands()
        if (res.isSuccessful) {
            return res.body()?.brands ?: emptyList()
        }
        return emptyList()
    }
}
