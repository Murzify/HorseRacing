package com.murzify.horseracing.core.database.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.murzify.horseracing.core.database.dao.RaceResultDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface RacingHistoryDao {
    @Insert
    fun insertResult(result: RaceResultDbo)

    @Query("SELECT * FROM RacingHistory")
    fun loadAllHistory(): Flow<List<RaceResultDbo>>
}