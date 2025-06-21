package com.murzify.horseracing.components.race.impl

import com.arkivanov.decompose.ComponentContext
import com.murzify.horseracing.components.race.api.RaceComponent
import com.murzify.horseracing.components.race.api.RaceComponent.Companion.HORSE_COUNT
import com.murzify.horseracing.components.race.api.RaceComponent.Model
import com.murzify.horseracing.components.race.api.RaceComponent.RaceState
import com.murzify.horseracing.core.common.componentCoroutineScope
import com.murzify.horseracing.core.data.api.HorseRacingRepository
import com.murzify.horseracing.core.domain.model.RaceResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import kotlin.math.min
import kotlin.random.Random

class DefaultRaceComponent @AssistedInject constructor(
    @Assisted val componentContext: ComponentContext,
    val horseRacingRepository: HorseRacingRepository
) : ComponentContext by componentContext, RaceComponent {
    private val scope = componentCoroutineScope()

    override val model = MutableStateFlow(
        Model(
            raceResult = null,
            horsePositions = List(HORSE_COUNT) { 0f },
            raceState = RaceState.NOT_STARTED
        )
    )

    @AssistedFactory
    interface Factory : RaceComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext
        ): DefaultRaceComponent
    }

    override fun onStartClicked() {
        scope.launch {
            model.update { Model(
                raceResult = null,
                horsePositions = List(HORSE_COUNT) { 0f },
                raceState = RaceState.NOT_STARTED
            ) }

            model.update { it.copy(raceState = RaceState.STARTED) }

            val result = simulateRace()

            model.update { it.copy(raceResult = result, raceState = RaceState.FINISHED) }

            withContext(Dispatchers.IO) {
                horseRacingRepository.saveResult(result)
            }
        }
    }

    private suspend fun simulateRace(): RaceResult {
        val updateInterval = 16L
        val startTime = System.currentTimeMillis()

        val speedFactors = List(HORSE_COUNT) { Random.nextFloat() * 0.4f + 0.8f }

        while (model.value.horsePositions.none { it >= 1f }) {
            delay(updateInterval)
            model.update {
                val newPositions = it.horsePositions.mapIndexed { index, position ->
                    val progress = Random.nextFloat() * 0.005f * speedFactors[index]
                    min(1f, position + progress)
                }
                it.copy(horsePositions = newPositions)
            }
        }
        val winnerIndex = model.value.horsePositions.indexOfFirst { it >= 1f }
        val duration = System.currentTimeMillis() - startTime

        return RaceResult(
            id = -1,
            winner = winnerIndex + 1,
            time = LocalDateTime.now(),
            durationMillis = duration.toInt()
        )
    }
}