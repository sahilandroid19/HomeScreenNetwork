package com.example.hmn

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.hmn.di.components.ApplicationComponent
import com.example.hmn.di.components.DaggerApplicationComponent
import com.example.hmn.di.modules.ApplicationModule
import com.example.hmn.utils.common.Constants
import com.example.hmn.utils.network.NetworkHelper
import javax.inject.Inject

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var networkHelper: NetworkHelper

    override fun onCreate() {
        super.onCreate()
        getDependencies()
        createNotificationChannel()
    }

    /*
    Function to inject dagger dependencies
     */
    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)

    }

    /*
    Function to create notification channel for Foreground Service
     */
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel: NotificationChannel = NotificationChannel(
                Constants.TAG,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}