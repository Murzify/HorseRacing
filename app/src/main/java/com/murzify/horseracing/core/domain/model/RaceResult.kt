package com.murzify.horseracing.core.domain.model

import java.time.LocalDateTime

data class RaceResult(
    val id: Int,
    val winner: Int,
    val time: LocalDateTime,
    val durationMillis: Long
)
