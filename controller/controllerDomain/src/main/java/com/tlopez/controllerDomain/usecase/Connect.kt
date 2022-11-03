package com.tlopez.controllerDomain.usecase

import com.tlopez.controllerDomain.TelloRepository
import javax.inject.Inject

class Connect @Inject constructor(
    private val telloRepository: TelloRepository
) {
    suspend operator fun invoke() {
        telloRepository.connect()
    }
}