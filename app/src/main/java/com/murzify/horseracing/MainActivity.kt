package com.murzify.horseracing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.retainedComponent
import com.murzify.horseracing.components.root.api.RootComponent
import com.murzify.horseracing.components.root.ui.RootContent
import com.murzify.horseracing.ui.theme.HorseRacingTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: RootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rootComponent = retainedComponent { componentContext ->
            rootComponentFactory(componentContext)
        }
        setContent {
            HorseRacingTheme {
                RootContent(rootComponent)
            }
        }
    }
}