package be.howest.jarivalentine.virtualcloset.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Brand(
    @SerialName("brand_name")
    var brandName: String,
    @SerialName("brand_logo_url")
    var brandLogoUrl: String? = null,
)
