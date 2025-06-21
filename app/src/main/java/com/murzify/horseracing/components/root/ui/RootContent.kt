package com.murzify.horseracing.components.root.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.murzify.horseracing.R
import com.murzify.horseracing.components.history.ui.HistoryContent
import com.murzify.horseracing.components.race.ui.RaceContent
import com.murzify.horseracing.components.root.api.RootComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    val stack = component.stack.subscribeAsState()
    val activeChild = stack.value.active.instance
    val selectedTabIndex = activeChild.toTabItem().ordinal

    Scaffold(
        modifier = modifier
    ) { contentPadding ->
        Column {
            PrimaryTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
            ) {
                TabItem.entries.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = when (tabItem) {
                            TabItem.RACE -> component::onRaceTabClicked
                            TabItem.HISTORY -> component::onHistoryTabClicked
                        },
                        text = {
                            Text(
                                text = stringResource(tabItem.labelRes),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                }
            }
            Children(
                stack = component.stack
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.Race -> RaceContent(child.component)
                    is RootComponent.Child.History -> HistoryContent(child.component)
                }
            }
        }
    }
}

private enum class TabItem(@StringRes val labelRes: Int) {
    RACE(R.string.race_tab), HISTORY(R.string.history_tab)
}

private fun RootComponent.Child.toTabItem() = when (this) {
    is RootComponent.Child.Race -> TabItem.RACE
    is RootComponent.Child.History -> TabItem.HISTORY
}