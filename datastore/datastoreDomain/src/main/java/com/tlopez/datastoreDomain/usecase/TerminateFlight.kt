package com.tlopez.datastoreDomain.usecase

import com.tlopez.datastoreDomain.models.TelloFlight
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

class TerminateFlight @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(
        hasSuccessfullyLanded: Boolean,
        telloFlight: TelloFlight
    ): Result<TelloFlight> {
        val lengthMs = telloFlight.startedMs.secondsSinceEpoch.let {
            currentTimeMillis() - SECONDS.toMillis(it)
        }
        check(lengthMs >= 0)
        return datastoreRepository.updateFlight(
            hasSuccessfullyLanded = hasSuccessfullyLanded,
            lengthMs = lengthMs,
            telloFlight = telloFlight
        )
    }
}