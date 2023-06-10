package be.howest.jarivalentine.virtualcloset.network

import be.howest.jarivalentine.virtualcloset.BuildConfig
import be.howest.jarivalentine.virtualcloset.data.Brand
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.Properties

interface BrandApiService {

    @Headers(BuildConfig.API_KEY)
    @GET("brands")
    suspend fun getBrands(): Response<QueryResponse>
}
