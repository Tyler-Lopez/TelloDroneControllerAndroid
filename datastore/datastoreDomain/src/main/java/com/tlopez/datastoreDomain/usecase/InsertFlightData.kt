package com.tlopez.datastoreDomain.usecase

import com.tlopez.datastoreDomain.models.TelloFlight
import com.tlopez.datastoreDomain.models.TelloFlightData
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class InsertFlightData @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(
        telloFlight: TelloFlight,
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
    ): Result<TelloFlightData> {
        val timeSinceStartMs = telloFlight.startedMs.secondsSinceEpoch.let {
            System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(it)
        }
        check(timeSinceStartMs >= 0)
        return datastoreRepository.insertFlightData(
            telloFlight.id,
            timeSinceStartMs,
            mid,
            x,
            y,
            z,
            mpry,
            pitch,
            roll,
            yaw,
            vgx,
            vgy,
            vgz,
            templ,
            temph,
            tof,
            h,
            bat,
            baro,
            time,
            agx,
            agy,
            agz
        )
    }
}