package com.tlopez.datastoreData

import android.content.Context
import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.datastoreDomain.repository.models.Challenge
import com.tlopez.datastoreDomain.repository.models.TelloFlight
import com.tlopez.datastoreDomain.repository.models.TelloFlightData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DatastoreRepositoryImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : DatastoreRepository {

    override suspend fun queryTelloFlightsByOwner(): Result<List<TelloFlight>> {
        TODO("Not yet implemented")
    }

    override suspend fun queryTelloFlightDataByTelloFlightId(id: String): Result<List<TelloFlightData>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertChallenge(name: String): Result<Unit> {
        return try {
            suspendCoroutine { continuation ->
                val item: Challenge = Challenge.builder()
                    .name(name)
                    .build()

                Amplify.DataStore.save(
                    item,
                    { success ->
                        println("here success")
                        continuation.resume(Result.success(Unit))
                        Log.i("Amplify", "Saved item: " + success.item().name)
                    },
                    { error ->
                        println("here error $error")
                        Log.e("Amplify", "Could not save item to DataStore", error)
                        continuation.resumeWithException(error)
                    }
                )
            }
        } catch (e: Exception) {
            println("exception caught $e")
            Result.failure(e)
        }
    }

    override suspend fun insertFlight(
        owner: String,
        startedMs: Long,
        lengthMs: Long,
        challengeId: String
    ): Result<TelloFlight> {
        return try {
            suspendCoroutine { continuation ->
                val item: TelloFlight = TelloFlight.builder()
                    .startedMs(Temporal.Timestamp(startedMs, TimeUnit.MILLISECONDS))
                    .challengeId(challengeId)
                    .owner(owner)
                    .lengthMs(lengthMs.toInt())
                    .build()

                Amplify.DataStore.save(
                    item,
                    { success ->
                        println("here success")
                        continuation.resume(Result.success(item))
                        Log.i("Amplify", "Saved item: " + success.item())
                    },
                    { error ->
                        println("here error $error")
                        Log.e("Amplify", "Could not save item to DataStore", error)
                        continuation.resumeWithException(error)
                    }
                )
            }
        } catch (e: Exception) {
            println("exception caught $e")
            Result.failure(e)
        }
    }

    override suspend fun insertFlightData(
        telloFlightId: String,
        timeSinceStartMs: Long,
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
    ): Result<TelloFlightData> {
        return try {
            suspendCoroutine { continuation ->
                val item: TelloFlightData = TelloFlightData.builder()
                    .timeSinceStartMs(timeSinceStartMs.toInt())
                    .telloflightId(telloFlightId)
                    .mpry(mpry)
                    .pitch(pitch)
                    .roll(roll)
                    .yaw(yaw)
                    .vgx(vgx)
                    .vgy(vgy)
                    .vgz(vgz)
                    .templ(templ)
                    .temph(temph)
                    .tof(tof)
                    .h(h)
                    .bat(bat)
                    .baro(baro.toDouble())
                    .time(time)
                    .agx(agx)
                    .agy(agy)
                    .agz(agz)
                    .build()
                Amplify.DataStore.save(
                    item,
                    { success ->
                        println("here success")
                        continuation.resume(Result.success(item))
                        Log.i("Amplify", "Saved item: ")
                    },
                    { error ->
                        println("here error $error")
                        Log.e("Amplify", "Could not save item to DataStore", error)
                        continuation.resumeWithException(error)
                    }
                )
            }
        } catch (e: Exception) {
            println("exception caught $e")
            Result.failure(e)
        }
    }

    override suspend fun updateFlight(
        telloFlight: TelloFlight,
        lengthMs: Long?
    ): Result<TelloFlight> {
        return try {
            suspendCoroutine { continuation ->
                val updatedFlight: TelloFlight = telloFlight
                    .copyOfBuilder()
                    .run {
                        lengthMs?.let { lengthMs(it.toInt()) } ?: this
                    }
                    .build()
                Amplify.DataStore.save(
                    updatedFlight,
                    { success ->
                        println("here success")
                        continuation.resume(Result.success(updatedFlight))
                        Log.i("Amplify", "Saved item: ")
                    },
                    { error ->
                        println("here error $error")
                        Log.e("Amplify", "Could not save item to DataStore", error)
                        continuation.resumeWithException(error)
                    }
                )
            }
        } catch (e: Exception) {
            println("exception caught $e")
            Result.failure(e)
        }
    }


}