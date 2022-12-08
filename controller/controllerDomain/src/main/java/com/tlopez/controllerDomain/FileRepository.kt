package com.tlopez.controllerDomain

import android.graphics.Bitmap

interface FileRepository {
    suspend fun saveBitmap(bitmap: Bitmap): Result<Unit>
}