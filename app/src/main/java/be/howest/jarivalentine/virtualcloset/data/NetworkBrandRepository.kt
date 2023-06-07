package be.howest.jarivalentine.virtualcloset.data

import be.howest.jarivalentine.virtualcloset.network.BrandApiService

class NetworkBrandRepository(
    private val brandApiService: BrandApiService
) : BrandRepository  {
    override suspend fun getBrands(): List<Brand> {
        return brandApiService.getBrands()
    }
}
