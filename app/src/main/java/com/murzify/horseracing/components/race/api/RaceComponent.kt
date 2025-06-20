package com.murzify.horseracing.components.race.api

import com.arkivanov.decompose.ComponentContext
import com.murzify.horseracing.core.domain.model.RaceResult
import kotlinx.coroutines.flow.StateFlow

interface RaceComponent {
    val model: StateFlow<Model>

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext
        ): RaceComponent
    }

    fun onStartClicked()

    data class Model(
        val raceResult: RaceResult?,
        val horsesDuration: List<Int>,
        val raceState: RaceState
    )

    enum class RaceState {
        NOT_STARTED,
        STARTED,
        FINISHED
    }
}