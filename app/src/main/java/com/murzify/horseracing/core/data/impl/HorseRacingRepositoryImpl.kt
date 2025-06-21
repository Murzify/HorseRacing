package com.murzify.horseracing.core.data.impl

import com.murzify.horseracing.core.data.api.HorseRacingRepository
import com.murzify.horseracing.core.database.model.RacingHistoryDao
import com.murzify.horseracing.core.domain.model.RaceResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HorseRacingRepositoryImpl @Inject constructor(
    private val racingHistoryDao: RacingHistoryDao
) : HorseRacingRepository{
    override fun getHistory(): Flow<List<RaceResult>> = racingHistoryDao.loadAllHistory()
        .map { it.toDomain() }

    override suspend fun saveResult(raceResult: RaceResult) {
        racingHistoryDao.insertResult(raceResult.toDbo())
    }
}