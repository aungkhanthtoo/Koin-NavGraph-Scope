package com.example.koinnavscope.nav.navigator

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.get
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Add the [content] [Composable] as bottom sheet content to the [NavGraphBuilder]
 *
 * @param route route for the destination
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content the sheet content at the given destination
 */
fun NavGraphBuilder.bottomSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) {
    destination(
        BottomSheetNavigatorDestinationBuilder(
            provider[BottomSheetNavigator::class],
            route,
            content
        )
            .apply {
                arguments.fastForEach { (argumentName, argument) ->
                    argument(argumentName, argument)
                }
                deepLinks.fastForEach { deepLink -> deepLink(deepLink) }
            }
    )
}

/**
 * Add the [content] [Composable] as bottom sheet content to the [NavGraphBuilder]
 *
 * @param T route from a [KClass] for the destination
 * @param typeMap map of destination arguments' kotlin type [KType] to its respective custom
 *   [NavType]. May be empty if [T] does not use custom NavTypes.
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content the sheet content at the given destination
 */
inline fun <reified T : Any> NavGraphBuilder.bottomSheet(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) {
    bottomSheet(T::class, typeMap, arguments, deepLinks, content)
}

@PublishedApi
internal fun NavGraphBuilder.bottomSheet(
    route: KClass<*>,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
    arguments: List<NamedNavArgument>,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable ColumnScope.(backstackEntry: NavBackStackEntry) -> Unit
) {
    destination(
        BottomSheetNavigatorDestinationBuilder(
            provider[BottomSheetNavigator::class],
            route,
            typeMap,
            content
        )
            .apply {
                arguments.fastForEach { (argumentName, argument) ->
                    argument(argumentName, argument)
                }
                deepLinks.fastForEach { deepLink -> deepLink(deepLink) }
            }
    )
}