package com.tlopez.storageDomain.usecase

import android.net.Uri
import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.storageDomain.repository.StorageRepository
import javax.inject.Inject
import kotlin.Result.Companion.failure

class UpdateAuthenticatedUserProfilePicture @Inject constructor(
    private val getUser: GetUser,
    private val storageRepository: StorageRepository
) {
    suspend operator fun invoke(
        fileUri: Uri,
        onProgressFraction: (Double) -> Unit
    ): Result<Unit> {
        return getUser().getOrNull()?.let {
            storageRepository.uploadFile(
                it.username,
                fileUri,
                onProgressFraction
            )
        } ?: run { failure(Exception()) }
    }
}