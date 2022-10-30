package com.tlopez.telloShare.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tlopez.telloShare.domain.models.Flight

@Entity
data class FlightEntity(
    @PrimaryKey
    override val unixMsStarted: Long,
    override val unixMsLength: Long
) : Flight