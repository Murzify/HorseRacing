package com.murzify.horseracing.components.root.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.murzify.horseracing.components.history.api.HistoryComponent
import com.murzify.horseracing.components.race.api.RaceComponent
import com.murzify.horseracing.components.root.api.RootComponent
import com.murzify.horseracing.components.root.api.RootComponent.Child
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultRootComponent @AssistedInject constructor(
    @Assisted componentContext: ComponentContext,
    private val raceComponentFactory: RaceComponent.Factory,
    private val historyComponentFactory: HistoryComponent.Factory
): ComponentContext by componentContext, RootComponent {
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Race,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext) =
        when (config) {
            Config.Race -> Child.Race(raceComponentFactory(componentContext))
            Config.History -> Child.History(historyComponentFactory(componentContext))
        }

    override fun onRaceTabClicked() {
        navigation.bringToFront(Config.Race)
    }

    override fun onHistoryTabClicked() {
        navigation.bringToFront(Config.History)
    }

    @AssistedFactory
    interface Factory : RootComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext
        ): DefaultRootComponent
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Race : Config

        @Serializable
        data object History : Config
    }
}