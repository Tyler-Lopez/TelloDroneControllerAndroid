package com.tlopez.datastoreDomain.repository

import com.tlopez.datastoreDomain.models.Challenge
import com.tlopez.datastoreDomain.models.TelloFlight
import com.tlopez.datastoreDomain.models.TelloFlightData

interface DatastoreRepository {
    suspend fun insertChallenge(name: String): Result<Challenge>
    suspend fun insertFlight(
        owner: String,
        startedMs: Long,
        challengeId: String?
    ): Result<TelloFlight>

    suspend fun insertFlightData(
        telloFlightId: String,
        timeSinceStartMs: Long,
        mid: Int,
        x: Int,
        y: Int,
        z: Int,
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
        agx: Float,
        agy: Float,
        agz: Float
    ): Result<TelloFlightData>

    suspend fun queryTelloFlightsByChallengeOrderedByLength(challengeId: String): Result<List<TelloFlight>>

    suspend fun updateFlight(
        hasSuccessfullyLanded: Boolean,
        lengthMs: Long,
        telloFlight: TelloFlight
    ): Result<TelloFlight>
}