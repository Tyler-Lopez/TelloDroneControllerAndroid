package com.tlopez.telloShare.architecture

interface Destination

interface Router<TypeOfDestination: Destination> {
    fun routeTo(destination: TypeOfDestination)
}