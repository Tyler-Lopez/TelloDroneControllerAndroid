package com.tlopez.datastoreDomain.di

import com.tlopez.authDomain.usecase.GetUser
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import com.tlopez.datastoreDomain.usecase.GetTelloFlightData
import com.tlopez.datastoreDomain.usecase.InitializeFlight
import com.tlopez.datastoreDomain.usecase.InsertFlightData
import com.tlopez.datastoreDomain.usecase.TerminateFlight
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DatastoreDomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetTelloFlightData(
        datastoreRepository: DatastoreRepository
    ): GetTelloFlightData {
        return GetTelloFlightData(datastoreRepository)
    }
    
    @Provides
    @ViewModelScoped
    fun provideInitializeFlight(
        getUser: GetUser,
        datastoreRepository: DatastoreRepository
    ): InitializeFlight {
        return InitializeFlight(datastoreRepository, getUser)
    }

    @Provides
    @ViewModelScoped
    fun provideFlightData(
        datastoreRepository: DatastoreRepository
    ): InsertFlightData {
        return InsertFlightData(datastoreRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideTerminateFlight(
        datastoreRepository: DatastoreRepository
    ): TerminateFlight {
        return TerminateFlight(datastoreRepository)
    }

}