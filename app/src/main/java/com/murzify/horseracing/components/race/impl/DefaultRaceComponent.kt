package com.murzify.horseracing.components.race.impl

import com.arkivanov.decompose.ComponentContext
import com.murzify.horseracing.components.race.api.RaceComponent
import com.murzify.horseracing.components.race.api.RaceComponent.Model
import com.murzify.horseracing.components.race.api.RaceComponent.RaceState
import com.murzify.horseracing.core.common.componentCoroutineScope
import com.murzify.horseracing.core.domain.model.RaceResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.random.Random

class DefaultRaceComponent @AssistedInject constructor(
    @Assisted val componentContext: ComponentContext,
//    val horseRacingRepository: HorseRacingRepository
) : ComponentContext by componentContext, RaceComponent {
    private val scope = componentCoroutineScope()

    override val model = MutableStateFlow(
        Model(
            raceResult = null,
            horsesDuration = List(4) { 0 },
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
        scope.launch(Dispatchers.IO) {
            model.update { Model(
                raceResult = null,
                horsesDuration = List(4) { 0 },
                raceState = RaceState.NOT_STARTED
            ) }

            val durations = List(4) { Random.nextInt(3000, 6000) }
            val winnerIndex = durations.withIndex().minBy { it.value }.index
            model.update { it.copy(horsesDuration = durations, raceState = RaceState.STARTED) }

            val winnerDuration = durations.min()
            delay(winnerDuration.toLong())

            val result = RaceResult(
                id = -1,
                winner = winnerIndex + 1,
                time = LocalDateTime.now(),
                durationMillis = winnerDuration
            )
            model.update { it.copy(raceResult = result, raceState = RaceState.FINISHED) }

//            horseRacingRepository.saveResult(result)
            println(model.value)
        }
    }
}