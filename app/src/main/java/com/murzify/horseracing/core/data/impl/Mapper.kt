package com.murzify.horseracing.core.data.impl

import com.murzify.horseracing.core.database.dao.RaceResultDbo
import com.murzify.horseracing.core.domain.model.RaceResult
import java.time.LocalDateTime

fun RaceResultDbo.toDomain() = RaceResult(
    id = id,
    winner = winner,
    time = LocalDateTime.parse(time),
    durationMillis = durationMillis
)

fun List<RaceResultDbo>.toDomain() = map { it.toDomain() }

fun RaceResult.toDbo() = RaceResultDbo(
    winner = winner,
    time = time.toString(),
    durationMillis = durationMillis
)