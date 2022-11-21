package com.tlopez.storageDomain.repository

import android.net.Uri
import java.io.File

interface StorageRepository {
    suspend fun downloadFile(fileKey: String): Result<String>
    suspend fun uploadFile(
        username: String,
        fileUri: Uri,
        onProgressFraction: (Double) -> Unit
    ): Result<Unit>
}

object NonJpgFileException : RuntimeException()