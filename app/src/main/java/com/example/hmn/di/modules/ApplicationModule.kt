package com.example.hmn.di.modules

import android.content.Context
import com.example.hmn.MyApplication
import com.example.hmn.di.qualifiers.ApplicationContext
import com.example.hmn.utils.location.LocationHelper
import com.example.hmn.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {

    @ApplicationContext
    @Provides
    fun provideContext() : Context = application

    @Provides
    fun provideCompositeDisposable() : CompositeDisposable =
        CompositeDisposable()

    @Singleton
    @Provides
    fun provideNetworkHelper() : NetworkHelper =
        NetworkHelper()

    @Singleton
    @Provides
    fun provideLocationHelper(@ApplicationContext context: Context) : LocationHelper =
        LocationHelper(context)
}