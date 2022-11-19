package com.tlopez.storageData

import android.content.Context
import android.net.Uri
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

    override suspend fun downloadFile(fileName: String): Result<String> {
        return try {
            Amplify.Storage.list(
                "",
                {
                    println("success list $it")
                    it.items.forEach {
                        println("herrree key is ${it.key}")
                    }
                },
                {
                    println("error list $it")
                }
            )
            println("hi here, filename is $fileName")
            suspendCoroutine { continuation ->
                Amplify.Storage.downloadFile(
                    fileName,
                    File("${context.cacheDir.absolutePath}/$fileName"),
                    { continuation.resume(success(it.file.toUri().toString())) },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            failure(e)
        }
    }
}