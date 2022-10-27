package com.tlopez.tello_controller.util

import java.lang.reflect.Type

inline fun <TypeOfData> Result<TypeOfData>.doOnSuccess(
    callback: (TypeOfData) -> Unit
): Result<TypeOfData> {
    if (isSuccess) callback(getOrNull()!!)
    return this
}

inline fun <TypeOfData> Result<TypeOfData>.doOnFailure(
    callback: (Throwable?) -> Unit
): Result<TypeOfData> {
    if (isFailure) callback(exceptionOrNull())
    return this
}
