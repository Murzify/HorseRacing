package com.murzify.horseracing.components.history.fake

import com.murzify.horseracing.components.history.api.HistoryComponent
import com.murzify.horseracing.core.domain.model.RaceResult
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

class FakeHistoryComponent : HistoryComponent {
    override val model = MutableStateFlow(HistoryComponent.Model(
        history = listOf(
            RaceResult(
                id = 1,
                winner = 2,
                time = LocalDateTime.now().minusDays(1),
                durationMillis = 4123
            ),
            RaceResult(
                id = 2,
                winner = 3,
                time = LocalDateTime.now().minusDays(2),
                durationMillis = 3890
            ),
            RaceResult(
                id = 3,
                winner = 1,
                time = LocalDateTime.now().minusDays(3),
                durationMillis = 4255
            ),
            RaceResult(
                id = 4,
                winner = 4,
                time = LocalDateTime.now().minusDays(4),
                durationMillis = 3777
            ),
            RaceResult(
                id = 5,
                winner = 2,
                time = LocalDateTime.now().minusDays(5),
                durationMillis = 4012
            )
        )
    ))
}