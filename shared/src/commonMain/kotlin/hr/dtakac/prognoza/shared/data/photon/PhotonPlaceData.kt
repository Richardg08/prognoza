package hr.dtakac.prognoza.shared.data.photon

import hr.dtakac.prognoza.shared.entity.Place
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PhotonResponse(
    @SerialName("features")
    val features: List<PhotonFeature>
)

@Serializable
internal data class PhotonFeature(
    @SerialName("geometry")
    val geometry: PhotonGeometry,
    @SerialName("properties")
    val properties: PhotonProperties
)

@Serializable
internal data class PhotonGeometry(
    @SerialName("coordinates")
    val coordinates: List<Double>
)

@Serializable
internal data class PhotonProperties(
    @SerialName("name")
    val name: String? = null,
    @SerialName("city")
    val city: String? = null,
    @SerialName("state")
    val state: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("osm_id")
    val osmId: Long? = null
)

internal fun PhotonFeature.toEntity(): Place {
    val name = properties.name ?: properties.city ?: properties.country ?: ""
    val details = listOfNotNull(
        properties.city.takeIf { it != name },
        properties.state,
        properties.country
    ).joinToString(", ")

    return Place(
        name = name,
        details = details,
        latitude = geometry.coordinates[1],
        longitude = geometry.coordinates[0]
    )
}
