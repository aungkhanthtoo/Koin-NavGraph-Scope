package com.example.koinnavscope.koin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import org.koin.compose.LocalKoinScope
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getScopeName
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


@OptIn(KoinInternalApi::class)
@Composable
internal fun KoinNavGraphScope(
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val koin = getKoin()
    val currentScope = LocalKoinScope.current

    var scope by remember {
        mutableStateOf(currentScope)
    }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val navScopeEntry = navController.getNavScopeEntryOrNull(destination, koin)
            scope = if (navScopeEntry == null) {
                koin.scopeRegistry.rootScope
            } else {
                koin.getScopeOrNull(navScopeEntry) ?: NavScopeComponent(navScopeEntry).scope
            }
        }
        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    CompositionLocalProvider(
        LocalKoinScope provides scope,
        content
    )
}

internal fun NavController.getNavScopeEntryOrNull(
    destination: NavDestination,
    koin: Koin
): NavBackStackEntry? {
    return getNavScopeRouteOrNull(destination, koin)?.let { route ->
        getBackStackEntry(route)
    }
}

internal fun NavController.getNavScopeRouteOrNull(
    destination: NavDestination,
    koin: Koin
): String? {
    val navScopes = koin.navScopes
    return destination.ancestors
        .mapNotNull { it.route }
        .firstOrNull { it in navScopes }
}

@OptIn(KoinInternalApi::class)
internal class NavScopeComponent(
    entry: NavBackStackEntry,
) : KoinScopeComponent, LifecycleEventObserver {

    private val route = requireNotNull(entry.destination.route)

    override val scope: Scope = getKoin().createScope(route, named(route))

    init {
        entry.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            scope.close()
        }
    }
}

internal val NavDestination.ancestors: Sequence<NavGraph>
    get() = generateSequence(parent) { it.parent }

internal fun Koin.getScopeOrNull(entry: NavBackStackEntry): Scope? {
    return getScopeOrNull(requireNotNull(entry.destination.route))
}

@OptIn(KoinInternalApi::class)
internal val Koin.navScopes: Set<String>
    get() {
        val rootScopeName = scopeRegistry.rootScope.getScopeName().value
        return scopeRegistry.scopeDefinitions
            .map { it.toString() }
            .filter { it != rootScopeName }
            .toSet()
    }
