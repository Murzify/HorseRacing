package com.murzify.horseracing.components.history.di

import com.murzify.horseracing.components.history.api.HistoryComponent
import com.murzify.horseracing.components.history.impl.DefaultHistoryComponent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HistoryModule {
    @Binds
    fun bindHistoryFactory(
        default: DefaultHistoryComponent.Factory
    ): HistoryComponent.Factory
}