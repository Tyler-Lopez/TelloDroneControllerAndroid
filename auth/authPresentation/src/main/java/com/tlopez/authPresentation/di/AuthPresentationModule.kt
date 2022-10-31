package com.tlopez.authPresentation.di

import com.tlopez.authDomain.repository.AuthRepository
import com.tlopez.authDomain.usecase.*
import com.tlopez.authPresentation.util.InputValidationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthPresentationModule {

    @ViewModelScoped
    @Provides
    fun provideInputValidationUtil(): InputValidationUtil {
        return InputValidationUtil()
    }
}