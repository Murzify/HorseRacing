package com.murzify.horseracing.components.race.fake

import com.murzify.horseracing.components.race.api.RaceComponent
import com.murzify.horseracing.core.domain.model.RaceResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class FakeRaceComponent : RaceComponent {
    val scope = CoroutineScope(Dispatchers.Default)
    override val model = MutableStateFlow(
        RaceComponent.Model(
            raceResult = RaceResult(
                id = -1,
                winner = 3,
                time = LocalDateTime.now(),
                durationMillis = 6000
            ),
            horsePositions = listOf(0f, 0f, 0f, 0f),
            raceState = RaceComponent.RaceState.NOT_STARTED
        )
    )

    override fun onStartClicked() {
        scope.launch {
            model.update { it.copy(raceState = RaceComponent.RaceState.NOT_STARTED) }
            model.update { it.copy(
                raceState = RaceComponent.RaceState.STARTED,
                horsePositions = listOf(.7f, .8f, 1f, .5f),
            ) }
            delay(model.value.raceResult!!.durationMillis.toLong())
            model.update { it.copy(raceState = RaceComponent.RaceState.FINISHED) }
        }
    }
}