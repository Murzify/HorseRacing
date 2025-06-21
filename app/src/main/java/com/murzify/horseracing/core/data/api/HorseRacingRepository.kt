package com.murzify.horseracing.core.data.api

import com.murzify.horseracing.core.domain.model.RaceResult
import kotlinx.coroutines.flow.Flow

interface HorseRacingRepository {

    fun getHistory(): Flow<List<RaceResult>>

    suspend fun saveResult(raceResult: RaceResult)
}