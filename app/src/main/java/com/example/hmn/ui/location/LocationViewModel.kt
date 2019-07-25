package com.example.hmn.ui.location

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import com.example.hmn.data.local.model.UserLocation
import com.example.hmn.ui.base.BaseViewModel
import com.example.hmn.utils.location.LocationHelper
import com.example.hmn.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class LocationViewModel(var compositeDisposable: CompositeDisposable,
                        var locationHelper: LocationHelper,
                        var networkHelper: NetworkHelper) :
    BaseViewModel(compositeDisposable,networkHelper), LifecycleObserver {

    private val locationData = MutableLiveData<UserLocation>()

    override fun onCreate() {
        locationHelper.registerReceiver()

        compositeDisposable.add(
            locationHelper.getLocationObservable()
                .subscribe { userLocation ->
                    locationData.postValue(userLocation)
                }
        )
    }

    fun getLocationData() = locationData

    fun onResume() {
        locationHelper.registerReceiver()
    }

    fun onPause() {
        locationHelper.unregisterReceiver()
    }

}