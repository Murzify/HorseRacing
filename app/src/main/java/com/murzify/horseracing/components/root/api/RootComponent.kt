package com.murzify.horseracing.components.root.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.murzify.horseracing.components.history.api.HistoryComponent
import com.murzify.horseracing.components.race.api.RaceComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }

    fun onRaceTabClicked()

    fun onHistoryTabClicked()

    sealed class Child {
        class Race(val component: RaceComponent) : Child()
        class History(val component: HistoryComponent) : Child()
    }
}