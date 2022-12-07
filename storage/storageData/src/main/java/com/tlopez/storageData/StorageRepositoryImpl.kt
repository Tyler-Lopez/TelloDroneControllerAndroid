package com.tlopez.storageData

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageUploadInputStreamOptions
import com.tlopez.storageDomain.repository.NonJpgFileException
import com.tlopez.storageDomain.repository.StorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class StorageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageRepository {

    companion object {
        private const val FILE_EXTENSION_JPG = "jpg"
        private const val FOLDER_PATH_PROFILE_PICTURE_RESIZED = "profile_picture/"
        private const val FOLDER_PATH_PROFILE_PICTURE_RAW = "to-be-resized/"
    }

    override suspend fun downloadFile(fileKey: String): Result<Uri> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Storage.downloadFile(
                    fileKey,
                    File("${context.cacheDir.absolutePath}/$fileKey"),
                    { continuation.resume(success(it.file.toUri())) },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun getUrl(fileKey: String): Result<String> {
        return try {
            suspendCoroutine { continuation ->
                Amplify.Storage.getUrl(
                    fileKey,
                    { continuation.resume(success(it.url.toString())) },
                    { continuation.resumeWithException(it) }
                )
            }
        } catch (e: Exception) {
            failure(e)
        }
    }

    override suspend fun uploadFile(
        username: String,
        fileUri: Uri,
        onProgressFraction: (Double) -> Unit
    ): Result<Unit> {
        return try {
            val fileType = MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(context.contentResolver.getType(fileUri))
            println("yo, file type is $fileType")
            if (fileType != FILE_EXTENSION_JPG) throw NonJpgFileException
            suspendCoroutine { continuation ->
                runCatching {
                    val stream = context.contentResolver.openInputStream(fileUri)
                    Amplify.Storage.uploadInputStream(
                        "$FOLDER_PATH_PROFILE_PICTURE_RAW$username.$FILE_EXTENSION_JPG",
                        stream!!,
                        StorageUploadInputStreamOptions.defaultInstance(),
                        { onProgressFraction(it.fractionCompleted) },
                        { continuation.resume(success(Unit)) },
                        { continuation.resumeWithException(it) }
                    )
                }
            }
        } catch (e: Exception) {
            println("Error caught: $e")
            failure(e)
        }
    }
}