package com.example.hmn.utils.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.hmn.R
import com.example.hmn.ui.location.LocationActivity
import com.example.hmn.utils.common.Constants
import com.example.hmn.utils.common.Toaster
import com.google.android.gms.location.*
import com.intentfilter.androidpermissions.PermissionManager
import com.intentfilter.androidpermissions.models.DeniedPermissions

class LocationService : Service() {

    private lateinit var locationClient: FusedLocationProviderClient

    private val locationRequest = LocationRequest()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, getNotification(getString(R.string.fetching_location)))

        locationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest.interval = Constants.INTERVAL
        locationRequest.fastestInterval = Constants.FASTEST_INTERVAL
      //locationRequest.smallestDisplacement = Constants.DISTANCE
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        requestLocation()
        return START_NOT_STICKY
    }

    /*
    Function to create notification object with text provided
     */
    private fun getNotification(text: String) : Notification {
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,
            0, Intent(this, LocationActivity::class.java), 0)

        return NotificationCompat.Builder(this, Constants.TAG).
            apply {
                setContentTitle(getString(R.string.location_update))
                setContentText(text)
                setSmallIcon(android.R.mipmap.sym_def_app_icon)
                setContentIntent(pendingIntent)
            }.build()
    }

    /*
    Function to update notification
     */
    private fun updateNotification(lat: String, long: String) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, getNotification("Latitude: $lat \n Longitude: $long"))
    }

    /*
    Function to check for location permissions
     */
    private fun requestLocation() {
        val permissionManager = PermissionManager.getInstance(applicationContext)
        permissionManager.checkPermissions(arrayListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION), object : PermissionManager.PermissionRequestListener{

            override fun onPermissionGranted() {
                fetchLocation()
            }

            override fun onPermissionDenied(p0: DeniedPermissions?) {
                Toaster.showToast(applicationContext, getString(R.string.permission_denied))
            }

        })
    }

    /*
    Function to fetch user Location
     */
    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        locationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                val locations = locationResult?.locations
                val location = locations?.get(locations.size - 1)
                updateNotification(location?.latitude.toString(), location?.longitude.toString())
                sendData(location?.latitude.toString(), location?.longitude.toString())
            }

        }, Looper.myLooper())
    }

    override fun onBind(p0: Intent?): IBinder? = null

    /*
    Function to send location data to Location helper class
     */
    private fun sendData(latitude: String, longitude: String) {
        val intent = Intent(Constants.ACTION_BROADCAST).apply {
            putExtra(Constants.EXTRA_LATITUDE, latitude)
            putExtra(Constants.EXTRA_LONGITUDE, longitude)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}