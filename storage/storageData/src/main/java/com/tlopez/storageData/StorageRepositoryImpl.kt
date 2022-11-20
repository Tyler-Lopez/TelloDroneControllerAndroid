package com.tlopez.storageData

import android.content.Context
import androidx.core.net.toUri
import com.amplifyframework.core.Amplify
import com.tlopez.storageDomain.repository.StorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StorageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageRepository {

    override suspend fun downloadFile(fileKey: String): Result<String> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Storage.downloadFile(
                    fileKey,
                    File("${context.cacheDir.absolutePath}/$fileKey"),
                    { continuation.resume(success(it.file.toUri().toString())) },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            failure(e)
        }
    }
}