package com.tlopez.tello_controller.domain.usecase

import com.tlopez.tello_controller.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<Unit> {
        return authRepository.signIn(username, password)
    }
}