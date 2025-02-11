package com.example.koinnavscope.nav.navigator

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavType
import kotlin.reflect.KClass
import kotlin.reflect.KType

/** DSL for constructing a new [BottomSheetNavigator.Destination] */
@NavDestinationDsl
class BottomSheetNavigatorDestinationBuilder :
    NavDestinationBuilder<BottomSheetNavigator.Destination> {

    private val bottomSheetNavigator: BottomSheetNavigator
    private val content: @Composable ColumnScope.(NavBackStackEntry) -> Unit

    /**
     * DSL for constructing a new [BottomSheetNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param route the destination's unique route
     * @param content composable for the destination
     */
    constructor(
        navigator: BottomSheetNavigator,
        route: String,
        content: @Composable ColumnScope.(NavBackStackEntry) -> Unit
    ) : super(navigator, route) {
        this.bottomSheetNavigator = navigator
        this.content = content
    }

    /**
     * DSL for constructing a new [BottomSheetNavigator.Destination]
     *
     * @param navigator navigator used to create the destination
     * @param route the destination's unique route from a [KClass]
     * @param typeMap map of destination arguments' kotlin type [KType] to its respective custom
     *   [NavType]. May be empty if [route] does not use custom NavTypes.
     * @param content composable for the destination
     */
    constructor(
        navigator: BottomSheetNavigator,
        route: KClass<*>,
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
        content: @Composable ColumnScope.(NavBackStackEntry) -> Unit
    ) : super(navigator, route, typeMap) {
        this.bottomSheetNavigator = navigator
        this.content = content
    }

    override fun instantiateDestination(): BottomSheetNavigator.Destination {
        return BottomSheetNavigator.Destination(bottomSheetNavigator, content)
    }
}