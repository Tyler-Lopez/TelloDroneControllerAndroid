package com.tlopez.storageDomain.usecase

import android.net.Uri
import com.amplifyframework.core.Amplify
import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.storageDomain.repository.StorageRepository
import javax.inject.Inject

class GetUserProfilePictureUrl @Inject constructor(
    private val storageRepository: StorageRepository
) {

    companion object {
        /** The folder in our S3 bucket which holds re-sized profile pictures **/
        private const val BUCKET_URL =
            "https://telloshare44af616297374cd6b766386e6abc2b8a231305-dev.s3.amazonaws.com/public/"
        private const val FOLDER_PATH_PROFILE_PICTURE = "profile_picture/"
        private const val FILE_EXTENSION_JPG = ".jpg"
    }

    suspend operator fun invoke(username: String): Result<String> {
        return storageRepository.getUrl("$FOLDER_PATH_PROFILE_PICTURE$username$FILE_EXTENSION_JPG")
    }
}