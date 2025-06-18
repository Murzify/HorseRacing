package com.murzify.horseracing.core.data.api

import com.murzify.horseracing.core.domain.model.RaceResult

interface HorseRacingRepository {

    fun getHistory(): List<RaceResult>

    fun saveResult(raceResult: RaceResult)
}