package com.tlopez.datastoreDomain.usecase

import com.tlopez.datastoreDomain.models.TelloFlightData
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import javax.inject.Inject

class GetTelloFlightData @Inject constructor(
    private val datastoreRepository: DatastoreRepository
){
    suspend operator fun invoke(flightId: String): Result<List<TelloFlightData>> {
        return datastoreRepository.queryTelloFlightData(flightId)
    }
}