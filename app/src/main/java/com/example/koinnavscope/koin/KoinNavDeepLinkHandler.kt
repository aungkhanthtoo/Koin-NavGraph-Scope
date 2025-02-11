package com.example.koinnavscope.koin

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import org.koin.compose.LocalKoinScope
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.ext.getFullName

@OptIn(KoinInternalApi::class)
@Composable
internal fun KoinNavDeepLinkHandler(
    navController: NavController,
    deepLinks: List<KoinNavDeepLink>,
    content: @Composable () -> Unit
) {
    val activity = LocalActivity.current
    val intentUri = activity?.intent?.data
    var currentScope = LocalKoinScope.current

    if (intentUri != null) {
        val koin = getKoin()
        val scopeRoute = deepLinks.find { (uriPattern, _) ->
            uriPattern.firstPathSegment == intentUri.firstPathSegment
        }?.scopeRoute

        if (scopeRoute != null) {
            val route = scopeRoute::class.getFullName()
            currentScope =
                koin.getScopeOrNull(route) ?: koin.createScope(route, named(route))
            CloseScopeEffect(navController, koin, currentScope)
        }
    }

    CompositionLocalProvider(
        LocalKoinScope provides currentScope,
        content
    )
}

@OptIn(KoinInternalApi::class)
@Composable
internal fun CloseScopeEffect(
    navController: NavController,
    koin: Koin,
    currentScope: Scope
) {
    DisposableEffect(Unit) {
        navController.addOnDestinationChangedListener(
            object : NavController.OnDestinationChangedListener,
                LifecycleEventObserver {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {
                    controller.removeOnDestinationChangedListener(this)
                    val navScopeEntry =
                        navController.getNavScopeEntryOrNull(destination, koin)
                    navScopeEntry?.lifecycle?.addObserver(this)
                }

                override fun onStateChanged(
                    source: LifecycleOwner,
                    event: Lifecycle.Event
                ) {
                    if (event == Lifecycle.Event.ON_DESTROY &&
                        currentScope != koin.scopeRegistry.rootScope
                    ) {
                        currentScope.close()
                    }
                }
            }
        )
        onDispose { }
    }
}

data class KoinNavDeepLink(
    val uriPattern: String,
    val scopeRoute: Any
)

private val String.firstPathSegment: String?
    get() = Uri.parse(this).firstPathSegment

private val Uri.firstPathSegment: String?
    get() = this.pathSegments.firstOrNull()