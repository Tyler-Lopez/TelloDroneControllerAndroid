package com.tlopez.datastoreData

import android.util.Log
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.core.model.temporal.Temporal
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.datastoreDomain.models.Challenge
import com.tlopez.datastoreDomain.models.TelloFlight
import com.tlopez.datastoreDomain.models.TelloFlightData
import java.util.concurrent.TimeUnit
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DatastoreRepositoryImpl : DatastoreRepository {

    companion object {
        private const val DEFAULT_CHALLENGE_ID = "ec885c24-3ea6-4583-880c-8b72a5213bab"
        private const val DEFAULT_UNTERMINATED_FLIGHT_LENGTH = -1
    }

    override suspend fun queryTelloFlightsByChallengeOrderedByLength(
        challengeId: String?
    ): Result<List<TelloFlight>> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.DataStore.query(
                    TelloFlight::class.java,
                    Where
                        .matches(TelloFlight.CHALLENGE_ID.eq(challengeId ?: DEFAULT_CHALLENGE_ID))
                        .matches(TelloFlight.SUCCESSFUL_LAND.eq(true))
                        .sorted(TelloFlight.LENGTH_MS.ascending()),
                    { continuation.resume(success(it.asSequence().toList())) },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            println("exception caught $e")
            Result.failure(e)
        }
    }

    override suspend fun insertChallenge(name: String): Result<Challenge> {
        return try {
            suspendCoroutine { continuation ->
                val item: Challenge = Challenge.builder()
                    .name(name)
                    .build()

                Amplify.DataStore.save(
                    item,
                    { success ->
                        println("here success")
                        continuation.resume(success(success.item()))
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
        challengeId: String?
    ): Result<TelloFlight> {
        return try {
            suspendCoroutine { continuation ->
                val item: TelloFlight = TelloFlight.builder()
                    .startedMs(Temporal.Timestamp(startedMs, TimeUnit.MILLISECONDS))
                    .challengeId(challengeId ?: DEFAULT_CHALLENGE_ID)
                    .owner(owner)
                    .lengthMs(DEFAULT_UNTERMINATED_FLIGHT_LENGTH)
                    .successfulLand(false)
                    .build()

                Amplify.DataStore.save(
                    item,
                    { success ->
                        continuation.resume(success(item))
                        Log.i("Amplify", "Saved item: " + success.item())
                    },
                    { error ->
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

    override suspend fun queryTelloFlightData(flightId: String): Result<List<TelloFlightData>> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.DataStore.query(
                    TelloFlightData::class.java,
                    Where
                        .matches(TelloFlightData.TELLOFLIGHT_ID.eq(flightId)),
                    { continuation.resume(success(it.asSequence().toList())) },
                    { continuation.resumeWithException(it) }
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
        mid: Int,
        x: Int,
        y: Int,
        z: Int,
        mPitch: Int,
        mRoll: Int,
        mYaw: Int,
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
        return try {
            suspendCoroutine { continuation ->
                val item: TelloFlightData = TelloFlightData.builder()
                    .timeSinceStartMs(timeSinceStartMs.toInt())
                    .telloflightId(telloFlightId)
                    .mid(mid)
                    .x(x)
                    .y(y)
                    .z(z)
                    .mpitch(mPitch)
                    .mroll(mRoll)
                    .myaw(mYaw)
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
                    .agx(agx.toDouble())
                    .agy(agy.toDouble())
                    .agz(agz.toDouble())
                    .build()
                println("prior to attempting to save with datastore telloflightdata")
                Amplify.DataStore.save(
                    item,
                    {
                        println("here success")
                        continuation.resume(success(item))
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
        hasSuccessfullyLanded: Boolean,
        lengthMs: Long,
        telloFlight: TelloFlight
    ): Result<TelloFlight> {
        return try {
            suspendCoroutine { continuation ->
                val updatedFlight: TelloFlight = telloFlight
                    .copyOfBuilder()
                    .lengthMs(lengthMs.toInt())
                    .successfulLand(hasSuccessfullyLanded)
                    .build()
                Amplify.DataStore.save(
                    updatedFlight,
                    {
                        println("here success")
                        continuation.resume(success(updatedFlight))
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