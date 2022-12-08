package com.tlopez.controllerData

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.tlopez.controllerDomain.FileRepository
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val context: Context
) : FileRepository {

    // https://stackoverflow.com/questions/36624756/how-to-save-bitmap-to-android-gallery
    override suspend fun saveBitmap(bitmap: Bitmap): Result<Unit> {
        return try {
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                val values = contentValues()

                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures")
                // values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
                values.put(MediaStore.Images.Media.IS_PENDING, true)
                // RELATIVE_PATH and IS_PENDING are introduced in API 29.

                val uri: Uri? =
                    context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values
                    )

                if (uri != null) {
                    saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    context.contentResolver.update(uri, values, null, null)
                }
            } else {
                println("here, in else")
                //    val directory = "${Environment.DIRECTORY_PICTURES}/$folderName"
                val directory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                )

                // */
                // getExternalStorageDirectory is deprecated in API 29

                //    if (!directory.exists()) {
                directory.mkdirs()
                //    }
                val fileName = System.currentTimeMillis().toString() + ".jpg"
                val file = File(directory, fileName)
                saveImageToStream(bitmap, FileOutputStream(file))
                val values = contentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}