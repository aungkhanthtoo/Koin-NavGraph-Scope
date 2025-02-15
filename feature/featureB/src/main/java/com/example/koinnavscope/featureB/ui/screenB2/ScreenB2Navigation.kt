package com.example.koinnavscope.featureB.ui.screenB2

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.example.koinnavscope.nav.KoinNavGraphBuilder
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object ScreenB2

internal fun KoinNavGraphBuilder.screenB2(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    composable<ScreenB2> {
        ScreenB2(onNavigateBack, onNavigateToHome, koinViewModel())
    }
}

fun NavController.navigateToScreenB2(builder: NavOptionsBuilder.() -> Unit) =
    navigate(ScreenB2, builder)
