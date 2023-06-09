package be.howest.jarivalentine.virtualcloset.network

import be.howest.jarivalentine.virtualcloset.data.Brand
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface BrandApiService {

    // TODO get the value out of a secret file
    @Headers("x-hasura-admin-secret: YAnZrGItz16rZX06MFimhwbZOHxPmNkrSe4pcBmLXkrkgiYzeQS04M5Mhjfb01KU")
    @GET("brands")
    suspend fun getBrands(): Response<QueryResponse>
}
