package com.example.koinnavscope.data.app

interface AppRepository {

    val counter: Int

    fun increaseCounter(): Int
}