package com.example.hmn.di.modules

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.hmn.di.qualifiers.ActivityContext
import com.example.hmn.ui.base.BaseActivity
import com.example.hmn.ui.location.LocationViewModel
import com.example.hmn.utils.common.ViewModelProviderFactory
import com.example.hmn.utils.location.LocationHelper
import com.example.hmn.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityContext
    @Provides
    fun provideContext() : Context = activity

    @Provides
    fun provideLocationViewModel(compositeDisposable: CompositeDisposable,
                                 locationHelper: LocationHelper,
                                 networkHelper: NetworkHelper) : LocationViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(LocationViewModel::class){
                LocationViewModel(compositeDisposable, locationHelper, networkHelper)
            }).get(LocationViewModel::class.java)


}