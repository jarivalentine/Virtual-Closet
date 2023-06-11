package be.howest.jarivalentine.virtualcloset.di

import android.content.Context
import be.howest.jarivalentine.virtualcloset.data.brand.BrandRepository
import be.howest.jarivalentine.virtualcloset.data.item.ItemRepository
import be.howest.jarivalentine.virtualcloset.data.brand.NetworkBrandRepository
import be.howest.jarivalentine.virtualcloset.data.item.OfflineItemRepository
import be.howest.jarivalentine.virtualcloset.data.outfit.OfflineOutfitRepository
import be.howest.jarivalentine.virtualcloset.data.outfit.OutfitRepository
import be.howest.jarivalentine.virtualcloset.data.VirtualClosetDatabase
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
    val json = Json { ignoreUnknownKeys = true }

    override val itemRepository: ItemRepository by lazy {
        OfflineItemRepository(VirtualClosetDatabase.getDatabase(context).itemDao())
    }

    override val outfitRepository: OutfitRepository by lazy {
        OfflineOutfitRepository(VirtualClosetDatabase.getDatabase(context).outfitDao())
    }

    private val BASE_URL =
        "https://virtual-closet.hasura.app/api/rest/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BrandApiService by lazy {
        retrofit.create(BrandApiService::class.java)
    }

    override val brandRepository: BrandRepository by lazy {
        NetworkBrandRepository(retrofitService)
    }
}
