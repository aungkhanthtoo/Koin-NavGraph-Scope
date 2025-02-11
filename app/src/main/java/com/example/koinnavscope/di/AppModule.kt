package com.example.koinnavscope.di

import com.example.koinnavscope.data.AppRepositoryImpl
import com.example.koinnavscope.data.app.AppRepository
import com.example.koinnavscope.ui.home.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appKoinModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::AppRepositoryImpl) bind AppRepository::class
}