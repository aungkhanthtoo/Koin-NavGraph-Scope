package com.example.koinnavscope.featureA.di

import com.example.koinnavscope.data.app.navScope
import com.example.koinnavscope.featureA.FeatureA
import com.example.koinnavscope.featureA.data.FeatureARepository
import com.example.koinnavscope.featureA.data.FeatureARepositoryImpl
import com.example.koinnavscope.featureA.ui.screenA1.ScreenA1ViewModel
import com.example.koinnavscope.featureA.ui.screenA2.ScreenA2ViewModel
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureAKoinScopeModule = module {
    navScope<FeatureA> {
        viewModelOf(::ScreenA1ViewModel)
        viewModelOf(::ScreenA2ViewModel)

        scopedOf(::FeatureARepositoryImpl) bind FeatureARepository::class
    }
}

