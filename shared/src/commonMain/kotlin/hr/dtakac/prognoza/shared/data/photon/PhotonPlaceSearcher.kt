package hr.dtakac.prognoza.shared.data.photon

import hr.dtakac.prognoza.shared.domain.data.PlaceSearcher
import hr.dtakac.prognoza.shared.domain.data.PlaceSearcherResult
import io.github.aakira.napier.Napier

private val TAG = PhotonPlaceSearcher::class.simpleName ?: ""

internal class PhotonPlaceSearcher(
    private val photonPlaceService: PhotonPlaceService
) : PlaceSearcher {
    override suspend fun search(
        query: String,
        rfc2616Language: String
    ): PlaceSearcherResult {
        val entities = try {
            photonPlaceService.search(query).features.map(PhotonFeature::toEntity)
        } catch (e: Exception) {
            Napier.e(TAG, e)
            null
        }
        return entities?.let {
            PlaceSearcherResult.Success(it)
        } ?: PlaceSearcherResult.Error
    }

    override suspend fun reverse(latitude: Double, longitude: Double): PlaceSearcherResult {
        val entities = try {
            photonPlaceService.reverse(latitude, longitude).features.map(PhotonFeature::toEntity)
        } catch (e: Exception) {
            Napier.e(TAG, e)
            null
        }
        return entities?.let {
            PlaceSearcherResult.Success(it)
        } ?: PlaceSearcherResult.Error
    }
}
