package com.murzify.horseracing.components.race.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.murzify.horseracing.R
import com.murzify.horseracing.components.race.api.RaceComponent
import com.murzify.horseracing.components.race.api.RaceComponent.RaceState
import com.murzify.horseracing.components.race.fake.FakeRaceComponent
import com.murzify.horseracing.ui.theme.HorseRacingTheme
import kotlin.math.roundToInt

@Composable
fun RaceContent(component: RaceComponent) {
    val model = component.model.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (model.value.raceState == RaceState.FINISHED
            && model.value.raceResult != null) {
            WinnerText(model.value.raceResult!!.winner)
        } else {
            model.value.horsesDuration.forEachIndexed { index, duration ->
                HorseLane(
                    index + 1,
                    duration.toInt(),
                    model.value.raceState == RaceState.STARTED
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            StartButton(model.value.raceState, component::onStartClicked)
        }
    }
}

@Composable
private fun HorseLane(horseNumber: Int, durationMillis: Int, isRunning: Boolean) {
    val iconSize = 48.dp
    val iconWidthPx = with(LocalDensity.current) { iconSize.toPx() }
    val horseOffset = remember { Animatable(0f) }
    var boxWidthPx by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(isRunning, boxWidthPx) {
        if (isRunning && boxWidthPx > 0) {
            horseOffset.snapTo(0f)
            horseOffset.animateTo(
                targetValue = boxWidthPx - iconWidthPx,
                animationSpec = tween(durationMillis, easing = LinearEasing)
            )
        } else if (!isRunning) {
            horseOffset.snapTo(0f)
        }
    }

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = horseNumber.toString(),
            style = MaterialTheme.typography.titleLarge
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .onGloballyPositioned { coordinates ->
                    boxWidthPx = coordinates.size.width.toFloat()
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.horse_racing),
                contentDescription = "Horse $horseNumber",
                modifier = Modifier
                    .offset { IntOffset(horseOffset.value.roundToInt(), 0) }
                    .size(iconSize)
            )
        }
        Icon(
            painter = painterResource(R.drawable.finish_icon),
            contentDescription = "Finish",
        )
    }
}

@Composable
fun WinnerText(winnerNumber: Int) {
    Box(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.winner, winnerNumber),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun StartButton(raceState: RaceState, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(32.dp).height(64.dp),
        onClick = onClick,
        enabled = raceState != RaceState.STARTED
    ) {
        val imageVector = if (raceState == RaceState.FINISHED) {
            Icons.Rounded.Refresh
        } else Icons.Rounded.PlayArrow
        Icon(
            imageVector = imageVector,
            contentDescription = "Play",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
private fun RaceContentPreview() {
    val fakeComponent = FakeRaceComponent()
    HorseRacingTheme {
        Surface {
            RaceContent(fakeComponent)
        }
    }

}