package com.tlopez.storageDomain.repository

import android.net.Uri

interface StorageRepository {
    suspend fun downloadFile(fileKey: String): Result<Uri>
    suspend fun getUrl(fileKey: String): Result<String>
    suspend fun uploadFile(
        username: String,
        fileUri: Uri,
        onProgressFraction: (Double) -> Unit
    ): Result<Unit>
}

object NonJpgFileException : RuntimeException()