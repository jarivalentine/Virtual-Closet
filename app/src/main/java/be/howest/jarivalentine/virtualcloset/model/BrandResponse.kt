package be.howest.jarivalentine.virtualcloset.model

import kotlinx.serialization.Serializable

@Serializable
data class BrandResponse(
    val brands: List<Brand>
)
