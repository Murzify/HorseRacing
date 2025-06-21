package com.murzify.horseracing.components.history.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.murzify.horseracing.R
import com.murzify.horseracing.components.history.api.HistoryComponent
import com.murzify.horseracing.components.history.fake.FakeHistoryComponent
import com.murzify.horseracing.core.domain.model.RaceResult
import com.murzify.horseracing.ui.theme.HorseRacingTheme
import java.time.format.DateTimeFormatter

@Composable
fun HistoryContent(component: HistoryComponent) {
    val model = component.model.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(model.value.history) { result ->
            RaceResultCard(result)
        }
    }
}

@Composable
private fun RaceResultCard(result: RaceResult, modifier: Modifier = Modifier) {
    val seconds = result.durationMillis / 1000.0
    val dateFormatted = result.time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.winner, result.winner),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.time, seconds),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.date, dateFormatted),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun RaceContentPreview() {
    val fakeComponent = FakeHistoryComponent()
    HorseRacingTheme {
        Surface {
            HistoryContent(fakeComponent)
        }
    }
}