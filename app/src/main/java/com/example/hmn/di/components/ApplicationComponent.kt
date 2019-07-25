package com.example.hmn.di.components

import com.example.hmn.MyApplication
import com.example.hmn.di.modules.ApplicationModule
import com.example.hmn.utils.location.LocationHelper
import com.example.hmn.utils.network.NetworkHelper
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    fun getNetworkHelper() : NetworkHelper

    fun getCompositeDisposable() : CompositeDisposable

    fun getLocationHelper() : LocationHelper
}