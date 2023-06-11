package be.howest.jarivalentine.virtualcloset.data.brand

import be.howest.jarivalentine.virtualcloset.model.Brand

interface BrandRepository {
    suspend fun getBrands(): List<Brand>
}
