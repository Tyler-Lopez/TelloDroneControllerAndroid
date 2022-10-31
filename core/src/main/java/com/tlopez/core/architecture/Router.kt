package com.tlopez.core.architecture

interface Destination

interface Router<TypeOfDestination: Destination> {
    fun routeTo(destination: TypeOfDestination)
}