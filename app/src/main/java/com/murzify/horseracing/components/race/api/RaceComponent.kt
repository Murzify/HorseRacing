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
        val horsePositions: List<Float>,
        val raceState: RaceState
    )

    enum class RaceState {
        NOT_STARTED,
        STARTED,
        FINISHED
    }

    companion object {
        const val HORSE_COUNT = 4
    }
}