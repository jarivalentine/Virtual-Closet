package be.howest.jarivalentine.virtualcloset.network

import be.howest.jarivalentine.virtualcloset.data.Brand
import kotlinx.serialization.Serializable

@Serializable
data class QueryResponse(
    val brands: List<Brand>
)
