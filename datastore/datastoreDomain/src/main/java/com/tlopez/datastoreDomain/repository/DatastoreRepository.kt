package com.tlopez.datastoreDomain.repository

import com.tlopez.datastoreDomain.repository.models.TelloFlight
import com.tlopez.datastoreDomain.repository.models.TelloFlightData

interface DatastoreRepository {
    suspend fun tempQueryAll()
    suspend fun insertChallenge(name: String): Result<Unit>
    suspend fun insertFlight(
        username: String,
        startedMs: Long,
        lengthMs: Long,
        challengeId: String
    ): Result<TelloFlight>

    suspend fun insertFlightData(
        telloFlightId: String,
        receivedAtMs: Long,
        mpry: Int,
        pitch: Int,
        roll: Int,
        yaw: Int,
        vgx: Int,
        vgy: Int,
        vgz: Int,
        templ: Int,
        temph: Int,
        tof: Int,
        h: Int,
        bat: Int,
        baro: Float,
        time: Int,
        agx: Int,
        agy: Int,
        agz: Int
    ): Result<TelloFlightData>

    suspend fun updateFlight(
        telloFlight: TelloFlight,
        lengthMs: Long?
    ): Result<TelloFlight>
}