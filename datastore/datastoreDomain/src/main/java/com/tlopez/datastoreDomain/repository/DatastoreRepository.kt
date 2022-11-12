package com.tlopez.datastoreDomain.repository

interface DatastoreRepository {
    suspend fun insertChallenge(name: String): Result<Unit>
}