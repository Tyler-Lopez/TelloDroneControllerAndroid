package com.tlopez.telloShare.util

sealed interface TelloResponse {
    object Error : TelloResponse
    object Ok : TelloResponse
}