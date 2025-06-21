package com.murzify.horseracing.components.history.impl

import com.arkivanov.decompose.ComponentContext
import com.murzify.horseracing.components.history.api.HistoryComponent
import com.murzify.horseracing.components.history.api.HistoryComponent.Model
import com.murzify.horseracing.core.common.componentCoroutineScope
import com.murzify.horseracing.core.data.api.HorseRacingRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultHistoryComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    repository: HorseRacingRepository
) : ComponentContext by componentContext, HistoryComponent {
    val scope = componentCoroutineScope()

    override val model: StateFlow<Model> = repository.getHistory()
        .map { Model(it) }
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), Model(emptyList()))

    @AssistedFactory
    interface Factory: HistoryComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext
        ): DefaultHistoryComponent
    }
}