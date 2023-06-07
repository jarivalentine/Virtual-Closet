package be.howest.jarivalentine.virtualcloset.data

import android.content.Context
import be.howest.jarivalentine.virtualcloset.network.BrandApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val itemRepository: ItemRepository
    val outfitRepository: OutfitRepository
    val brandRepository: BrandRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val itemRepository: ItemRepository by lazy {
        OfflineItemRepository(VirtualClosetDatabase.getDatabase(context).itemDao())
    }

    override val outfitRepository: OutfitRepository by lazy {
        OfflineOutfitRepository(VirtualClosetDatabase.getDatabase(context).outfitDao())
    }

    private val BASE_URL =
        "https://virtual-closet.hasura.app/api/rest"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BrandApiService by lazy {
        retrofit.create(BrandApiService::class.java)
    }

    override val brandRepository: BrandRepository by lazy {
        NetworkBrandRepository(retrofitService)
    }
}
