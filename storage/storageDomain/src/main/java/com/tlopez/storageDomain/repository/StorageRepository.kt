package com.tlopez.storageDomain.repository

import java.io.File

interface StorageRepository {
    suspend fun downloadFile(fileName: String): Result<String>
}