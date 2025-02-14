package com.example.koinnavscope.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.koinnavscope.nav.navigator.bottomSheet
import kotlin.reflect.KClass
import kotlin.reflect.KType

inline fun <reified T : Any> NavGraphBuilder.navigationScope(
    startDestination: Any,
    controller: NavController,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    builder: KoinNavGraphBuilder.() -> Unit
): Unit = destination(
    KoinNavGraphBuilder(
        provider,
        startDestination,
        T::class,
        typeMap,
        controller
    ).apply(builder)
)

inline fun <reified T : Any> KoinNavGraphBuilder.navigationScope(
    startDestination: Any,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    builder: KoinNavGraphBuilder.() -> Unit
): Unit = destination(
    KoinNavGraphBuilder(
        provider,
        startDestination,
        T::class,
        typeMap,
        controller
    ).apply(builder)
)

class KoinNavGraphBuilder(
    provider: NavigatorProvider,
    startDestination: Any,
    route: KClass<*>?,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
    val controller: NavController
) : NavGraphBuilder(provider, startDestination, route, typeMap)

inline fun <reified T : Any> KoinNavGraphBuilder.composableNavScope(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        null,
    noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        null,
    noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? =
        enterTransition,
    noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? =
        exitTransition,
    noinline sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    SizeTransform?)? =
        null,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
        sizeTransform
    ) {
        KoinNavGraphScope(it, controller) {
            content(it)
        }
    }
}

inline fun <reified T : Any> KoinNavGraphBuilder.dialogNavScope(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    dialogProperties: DialogProperties = DialogProperties(),
    noinline content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog<T>(typeMap, deepLinks, dialogProperties) {
        KoinNavGraphScope(it, controller) {
            content(it)
        }
    }
}

inline fun <reified T : Any> KoinNavGraphBuilder.bottomSheetNavScope(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) {
    bottomSheet<T>(typeMap, arguments, deepLinks) {
        KoinNavGraphScope(it, controller) {
            content(it)
        }
    }
}