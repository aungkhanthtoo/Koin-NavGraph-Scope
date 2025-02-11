package com.example.koinnavscope.koin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import org.koin.compose.LocalKoinScope
import org.koin.compose.getKoin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.qualifier.named

@OptIn(KoinInternalApi::class)
@Composable
internal fun KoinNavSaveStateHandler(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val koin = getKoin()
    var currentScope = LocalKoinScope.current

    var lastScopeRoute: String? by rememberSaveable {
        mutableStateOf(null)
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { controller, destination, _ ->
            lastScopeRoute = if (destination is FloatingWindow) { // Ignore FloatingWindows
                lastScopeRoute
            } else { // Save only last Composable route for restoring save state
                controller.getNavScopeRouteOrNull(destination, koin)
            }
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    val route = lastScopeRoute
    if (route != null) {
        currentScope = koin.getScopeOrNull(route) ?: koin.createScope(route, named(route))
    }
    CloseScopeEffect(navController, koin, currentScope)

    CompositionLocalProvider(
        LocalKoinScope provides currentScope,
        content
    )
}