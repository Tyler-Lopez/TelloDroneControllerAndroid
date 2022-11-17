package com.tlopez.storageData

import android.content.Context
import com.amplifyframework.core.Amplify
import com.tlopez.storageDomain.repository.StorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StorageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageRepository {

    override suspend fun getFile(fileName: String): Result<File> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Storage.downloadFile(
                    fileName,
                    context.cacheDir,
                    { continuation.resume(success(it.file)) },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            error(e)
        }
    }
}