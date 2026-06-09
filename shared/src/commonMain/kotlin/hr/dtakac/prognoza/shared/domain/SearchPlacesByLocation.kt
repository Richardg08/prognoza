package hr.dtakac.prognoza.shared.domain

import hr.dtakac.prognoza.shared.domain.data.PlaceSearcher
import hr.dtakac.prognoza.shared.domain.data.PlaceSearcherResult
import hr.dtakac.prognoza.shared.entity.Place

class SearchPlacesByLocation internal constructor(
    private val placeSearcher: PlaceSearcher
) {
    suspend operator fun invoke(latitude: Double, longitude: Double): SearchPlacesResult {
        val result = placeSearcher.reverse(latitude, longitude)
        return if (result is PlaceSearcherResult.Success) {
            if (result.places.isEmpty()) {
                SearchPlacesResult.Empty.None
            } else {
                SearchPlacesResult.Success(result.places)
            }
        } else SearchPlacesResult.Empty.Error
    }
}
