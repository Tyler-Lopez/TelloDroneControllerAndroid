package com.tlopez.datastoreDomain.usecase

import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.datastoreDomain.models.TelloFlight
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import java.lang.System.currentTimeMillis
import javax.inject.Inject

class InitializeFlight @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val getUser: GetUser
) {
    suspend operator fun invoke(challengeId: String? = null): Result<TelloFlight> {
        return getUser().getOrNull()?.let {
            datastoreRepository.insertFlight(
                owner = it.username,
                startedMs = currentTimeMillis(),
                challengeId = challengeId
            )
        } ?: run { Result.failure(Exception()) }
    }
}