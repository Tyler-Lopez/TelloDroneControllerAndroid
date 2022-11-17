package com.tlopez.storageDomain.repository

import java.io.File

interface StorageRepository {
    suspend fun getFile(fileName: String): Result<File>
}