package com.example.koinnavscope.featureB.di

import com.example.koinnavscope.featureB.FeatureB
import com.example.koinnavscope.featureB.data.FeatureBRepository
import com.example.koinnavscope.featureB.data.FeatureBRepositoryImpl
import com.example.koinnavscope.featureB.ui.screenB1.ScreenB1ViewModel
import com.example.koinnavscope.featureB.ui.screenB2.ScreenB2ViewModel
import com.example.koinnavscope.nav.navScope
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureBKoinScopeModule = module {
    navScope<FeatureB> {
        viewModelOf(::ScreenB1ViewModel)
        viewModelOf(::ScreenB2ViewModel)

        scopedOf(::FeatureBRepositoryImpl) bind FeatureBRepository::class
    }
}

