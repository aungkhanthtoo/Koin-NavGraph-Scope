package com.example.koinnavscope.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
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
fun NavGraphKoinScope(
    entry: NavBackStackEntry,
    controller: NavController,
    content: @Composable () -> Unit
) {
    val koin = getKoin()
    var scope = LocalKoinScope.current

    val destination = entry.destination
    val navScopeEntry = remember(controller, destination) {
        controller.getNavScopeEntryOrNull(destination, koin)
    }
    scope = if (navScopeEntry != null) {
        koin.getScopeOrNull(navScopeEntry) ?: NavScopeComponent(navScopeEntry).scope
    } else {
        koin.scopeRegistry.rootScope
    }

    CompositionLocalProvider(
        LocalKoinScope provides scope,
        content
    )
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
            Log.d("NavGraphKoinScope", "close scope $scope")
            scope.close()
        }
    }
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