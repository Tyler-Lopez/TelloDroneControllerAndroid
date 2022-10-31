package com.tlopez.authDomain.usecase

import com.tlopez.authDomain.repository.AuthRepository
import javax.inject.Inject

class LogoutUser @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}