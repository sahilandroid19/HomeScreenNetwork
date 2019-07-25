package com.example.hmn.utils.common

import com.example.hmn.utils.location.LocationService

object Constants {

    val TAG: String = LocationService::class.java.name

    const val INTERVAL = 10000L

    const val FASTEST_INTERVAL = 10000L

    const val DISTANCE = 50f

    val ACTION_BROADCAST = "$TAG LocationService"

    const val EXTRA_LATITUDE = "extra_latitude"

    const val EXTRA_LONGITUDE = "extra_longitude"

}