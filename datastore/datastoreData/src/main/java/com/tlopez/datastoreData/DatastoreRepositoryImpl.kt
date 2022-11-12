package com.tlopez.datastoreData

import android.content.Context
import android.util.Log
import com.amplifyframework.core.Amplify
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.datastoreDomain.repository.models.Challenge
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DatastoreRepositoryImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : DatastoreRepository {

    init {
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


}