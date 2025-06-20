package com.murzify.horseracing.core.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RacingHistory")
data class RaceResultDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val winner: Int,
    val time: String,
    val durationMillis: Int
)
