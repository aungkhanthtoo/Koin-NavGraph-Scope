package com.example.koinnavscope

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.koinnavscope.di.appKoinModule
import com.example.koinnavscope.featureA.di.featureAKoinScopeModule
import com.example.koinnavscope.featureB.di.featureBKoinScopeModule
import com.example.koinnavscope.featureC.di.featureCKoinScopeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinNavScopeApplication : Application() {

    override fun onCreate() {
        createNotificationChannel(this)
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                appKoinModule,
                featureAKoinScopeModule,
                featureBKoinScopeModule,
                featureCKoinScopeModule
            )
        }
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "Koin"
        val channelName = "My Channel Name"
        val channelDescription = "Description of my channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }

        val notificationManager: NotificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}