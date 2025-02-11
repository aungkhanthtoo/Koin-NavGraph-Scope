package com.example.koinnavscope.featureB.di

import com.example.koinnavscope.featureB.data.FeatureBRepository
import com.example.koinnavscope.featureB.data.FeatureBRepositoryImpl
import com.example.koinnavscope.featureB.ui.screenB1.ScreenB1ViewModel
import com.example.koinnavscope.featureB.ui.screenB2.ScreenB2ViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val featureBKoinModule = module {
    viewModelOf(::ScreenB1ViewModel)
    viewModelOf(::ScreenB2ViewModel)

    singleOf(::FeatureBRepositoryImpl) bind FeatureBRepository::class
}