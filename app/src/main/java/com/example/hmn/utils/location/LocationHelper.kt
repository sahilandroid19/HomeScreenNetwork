package com.example.hmn.utils.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.hmn.data.local.model.UserLocation
import com.example.hmn.di.qualifiers.ApplicationContext
import com.example.hmn.utils.common.Constants
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

/*
Helper class for receiving location updates
 */
@Singleton
class LocationHelper(@ApplicationContext var context: Context) {

    private var subject: PublishSubject<UserLocation> = PublishSubject.create()

    private val broadcastReceiver: BroadcastReceiver

    init {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val userLocation = UserLocation(
                    p1?.getStringExtra(Constants.EXTRA_LATITUDE),
                    p1?.getStringExtra(Constants.EXTRA_LONGITUDE)
                )
                subject.onNext(userLocation)
            }
        }
    }

    /*
    Function to register local broadcast receiver
     */
    fun registerReceiver() {
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver,
            IntentFilter(Constants.ACTION_BROADCAST))
    }

    fun getLocationObservable() = subject

    fun unregisterReceiver() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
    }
}