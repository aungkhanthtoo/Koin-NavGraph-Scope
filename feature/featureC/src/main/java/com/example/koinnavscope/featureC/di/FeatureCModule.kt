package com.example.koinnavscope.featureC.di

import com.example.koinnavscope.featureC.data.FeatureCRepository
import com.example.koinnavscope.featureC.data.FeatureCRepositoryImpl
import com.example.koinnavscope.featureC.ui.screenC1.ScreenC1ViewModel
import com.example.koinnavscope.featureC.ui.screenC2.ScreenC2ViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureCKoinModule = module {
    viewModelOf(::ScreenC1ViewModel)
    viewModelOf(::ScreenC2ViewModel)

    singleOf(::FeatureCRepositoryImpl) bind FeatureCRepository::class
}