package com.tlopez.storageDomain.di

import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.storageDomain.repository.StorageRepository
import com.tlopez.storageDomain.usecase.GetUserProfilePictureUrl
import com.tlopez.storageDomain.usecase.UpdateAuthenticatedUserProfilePicture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object StorageDomainModule {

    @Provides
    @ViewModelScoped
    fun provideUpdateAuthenticatedUserProfilePicture(
        getUser: GetUser,
        storageRepository: StorageRepository
    ): UpdateAuthenticatedUserProfilePicture {
        return UpdateAuthenticatedUserProfilePicture(getUser, storageRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUserProfilePictureUrl(storageRepository: StorageRepository): GetUserProfilePictureUrl {
        return GetUserProfilePictureUrl(storageRepository)
    }
}