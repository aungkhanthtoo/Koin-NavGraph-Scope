package com.example.koinnavscope.featureC.di

import com.example.koinnavscope.data.app.navScope
import com.example.koinnavscope.featureC.FeatureC
import com.example.koinnavscope.featureC.data.FeatureCRepository
import com.example.koinnavscope.featureC.data.FeatureCRepositoryImpl
import com.example.koinnavscope.featureC.ui.screenC1.ScreenC1ViewModel
import com.example.koinnavscope.featureC.ui.screenC2.ScreenC2ViewModel
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureCKoinScopeModule = module {
    navScope<FeatureC> {
        viewModelOf(::ScreenC1ViewModel)
        viewModelOf(::ScreenC2ViewModel)

        scopedOf(::FeatureCRepositoryImpl) bind FeatureCRepository::class
    }
}

