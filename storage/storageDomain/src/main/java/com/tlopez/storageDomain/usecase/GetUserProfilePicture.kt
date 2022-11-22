package com.tlopez.storageDomain.usecase

import android.net.Uri
import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.storageDomain.repository.StorageRepository
import javax.inject.Inject

class GetUserProfilePicture @Inject constructor(
    private val getUser: GetUser,
    private val storageRepository: StorageRepository
) {
    companion object {
        /** The folder in our S3 bucket which holds re-sized profile pictures **/
        private const val FOLDER_PATH_PROFILE_PICTURE = "profile_picture/"
        private const val FILE_EXTENSION_JPG = ".jpg"
    }

    suspend operator fun invoke(): Result<Uri> {
        return getUser().getOrNull()?.let {
            storageRepository.downloadFile(
                fileKey = "${FOLDER_PATH_PROFILE_PICTURE}${it.username}$FILE_EXTENSION_JPG"
            )
        } ?: run { Result.failure(Exception()) }
    }
}