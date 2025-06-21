package com.murzify.horseracing.components.root.di

import com.murzify.horseracing.components.root.api.RootComponent
import com.murzify.horseracing.components.root.impl.DefaultRootComponent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RootModule {
    @Binds
    fun bindRootComponentFactory(
        default: DefaultRootComponent.Factory
    ): RootComponent.Factory
}