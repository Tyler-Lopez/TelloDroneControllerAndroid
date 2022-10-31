package com.tlopez.authDomain.di

import com.tlopez.authDomain.repository.AuthRepository
import com.tlopez.authDomain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AuthDomainModule {

    @ViewModelScoped
    @Provides
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            getUser = GetUser(repository),
            logoutUser = LogoutUser(repository),
            registerUser = RegisterUser(repository),
            resendVerification = ResendVerification(repository),
            signInUser = SignInUser(repository),
            verifyUser = VerifyUser(repository)
        )
    }
}