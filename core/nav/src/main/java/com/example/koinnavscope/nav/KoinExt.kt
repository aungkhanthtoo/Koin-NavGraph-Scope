package com.example.koinnavscope.nav

import org.koin.core.module.KoinDslMarker
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.ScopeDSL
import org.koin.ext.getFullName

@KoinDslMarker
inline fun <reified T : Any> Module.navScope(noinline scopeSet: ScopeDSL.() -> Unit) {
    scope(named(T::class.getFullName()), scopeSet)
}