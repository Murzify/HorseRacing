package com.murzify.horseracing.core.data.di

import com.murzify.horseracing.core.data.api.HorseRacingRepository
import com.murzify.horseracing.core.data.impl.HorseRacingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Binds
    fun bindHorseRacingRepository(impl: HorseRacingRepositoryImpl): HorseRacingRepository
}