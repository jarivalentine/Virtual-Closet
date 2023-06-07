package be.howest.jarivalentine.virtualcloset.network

import be.howest.jarivalentine.virtualcloset.data.Brand
import retrofit2.http.GET

interface BrandApiService {

    @GET("brands")
    suspend fun getBrands(): List<Brand>
}