package com.murzify.horseracing.core.database.di

import android.content.Context
import androidx.room.Room
import com.murzify.horseracing.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "horse-racing"
    ).build()

    @Provides
    fun provideRacingHistoryDao(db: AppDatabase) = db.racingHistoryDao()
}