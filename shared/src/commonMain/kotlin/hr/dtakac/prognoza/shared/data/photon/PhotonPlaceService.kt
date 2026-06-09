package hr.dtakac.prognoza.shared.data.photon

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class PhotonPlaceService(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun search(query: String): PhotonResponse = client
        .get(urlString = "$baseUrl/api/") {
            parameter("q", query)
        }
        .body()

    suspend fun reverse(latitude: Double, longitude: Double): PhotonResponse = client
        .get(urlString = "$baseUrl/reverse") {
            parameter("lat", latitude)
            parameter("lon", longitude)
        }
        .body()
}
