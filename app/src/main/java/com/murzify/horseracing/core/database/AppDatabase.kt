package com.murzify.horseracing.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.murzify.horseracing.core.database.dao.RaceResultDbo
import com.murzify.horseracing.core.database.model.RacingHistoryDao

@Database(entities = [RaceResultDbo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun racingHistoryDao(): RacingHistoryDao
}