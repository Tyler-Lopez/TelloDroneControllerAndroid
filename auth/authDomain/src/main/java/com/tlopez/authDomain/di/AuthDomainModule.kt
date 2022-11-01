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
    fun provideGetUserUseCase(repository: AuthRepository): GetUser {
        return GetUser(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideLogoutUserUseCase(repository: AuthRepository): LogoutUser {
        return LogoutUser(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideRegisterUserUseCase(repository: AuthRepository): RegisterUser {
        return RegisterUser(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideResendVerificationUseCase(repository: AuthRepository): ResendVerification {
        return ResendVerification(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideSignInUserUseCase(repository: AuthRepository): SignInUser {
        return SignInUser(repository)
    }

    @ViewModelScoped
    @Provides
    fun provideVerifyUserUseCase(repository: AuthRepository): VerifyUser {
        return VerifyUser(repository)
    }
}