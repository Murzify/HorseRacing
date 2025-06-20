package com.murzify.horseracing.components.race.di

import com.murzify.horseracing.components.race.api.RaceComponent
import com.murzify.horseracing.components.race.impl.DefaultRaceComponent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RaceModule {
    @Binds
    fun bindRaceComponentFactory(default: DefaultRaceComponent.Factory): RaceComponent.Factory
}