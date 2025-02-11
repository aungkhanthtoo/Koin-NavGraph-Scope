package com.example.koinnavscope.featureA.di

import com.example.koinnavscope.featureA.data.FeatureARepository
import com.example.koinnavscope.featureA.data.FeatureARepositoryImpl
import com.example.koinnavscope.featureA.ui.screenA1.ScreenA1ViewModel
import com.example.koinnavscope.featureA.ui.screenA2.ScreenA2ViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureAKoinModule = module {
    viewModelOf(::ScreenA1ViewModel)
    viewModelOf(::ScreenA2ViewModel)

    singleOf(::FeatureARepositoryImpl) bind FeatureARepository::class
}