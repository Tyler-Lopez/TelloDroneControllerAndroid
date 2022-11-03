package com.tlopez.controllerDomain.di

import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.usecase.Connect
import com.tlopez.controllerDomain.usecase.TelloUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ControllerDomainModule {
    @ViewModelScoped
    @Provides
    fun provideTelloUseCases(repository: TelloRepository): TelloUseCases {
        return object : TelloUseCases {
            override val connect: Connect = Connect(repository)
        }
    }
}