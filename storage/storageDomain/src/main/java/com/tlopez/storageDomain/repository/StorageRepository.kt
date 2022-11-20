package com.tlopez.storageDomain.repository

import java.io.File

interface StorageRepository {
    suspend fun downloadFile(fileKey: String): Result<String>
    suspend fun uploadFile(fileUrl: String): Result<String>
}