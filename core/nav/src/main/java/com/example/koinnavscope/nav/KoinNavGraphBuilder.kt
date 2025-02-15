package com.example.koinnavscope.nav

import androidx.navigation.NavDestination
import androidx.navigation.NavDestinationBuilder
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder

class KoinNavGraphBuilder(
    private val delegate: NavGraphBuilder,
    override val provider: NavGraphScopeProvider
) : NavGraphScopeBuilder {

    override fun <D : NavDestination> destination(navDestination: NavDestinationBuilder<D>) {
        delegate.destination(navDestination)
    }

    fun build(): NavGraph = delegate.build()
}
