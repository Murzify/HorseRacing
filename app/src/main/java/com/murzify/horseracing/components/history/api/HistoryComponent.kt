package com.murzify.horseracing.components.history.api

import com.arkivanov.decompose.ComponentContext
import com.murzify.horseracing.core.data.api.HorseRacingRepository
import com.murzify.horseracing.core.domain.model.RaceResult
import kotlinx.coroutines.flow.StateFlow

interface HistoryComponent {
    val model: StateFlow<Model>

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext
        ): HistoryComponent
    }

    data class Model(
        val history: List<RaceResult>
    )
}