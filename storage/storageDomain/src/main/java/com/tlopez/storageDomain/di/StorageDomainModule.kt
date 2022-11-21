package com.tlopez.storageDomain.di

import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.storageDomain.repository.StorageRepository
import com.tlopez.storageDomain.usecase.GetUserProfilePicture
import com.tlopez.storageDomain.usecase.UpdateUserProfilePicture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageDomainModule {

    @Provides
    @Singleton
    fun provideUpdateUserProfilePicture(
        getUser: GetUser,
        storageRepository: StorageRepository
    ): UpdateUserProfilePicture {
        return UpdateUserProfilePicture(getUser, storageRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserProfilePicture(
        getUser: GetUser,
        storageRepository: StorageRepository
    ): GetUserProfilePicture {
        return GetUserProfilePicture(getUser, storageRepository)
    }
}