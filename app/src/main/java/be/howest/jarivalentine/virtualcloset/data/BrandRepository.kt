package be.howest.jarivalentine.virtualcloset.data

import be.howest.jarivalentine.virtualcloset.network.BrandApiService

interface BrandRepository {
    suspend fun getBrands(): List<Brand>
}
