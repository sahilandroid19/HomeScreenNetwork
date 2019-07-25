package com.example.hmn.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hmn.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

/*
Base class for all ViewModels containing common code
 */
abstract class BaseViewModel(private val compositeDisposable: CompositeDisposable,
                             private val networkHelper: NetworkHelper) : ViewModel() {

    val messageString = MutableLiveData<String>()

    /*
    Function to check internet connection
     */
    fun checkInternetConnection() : Boolean {
        return if(networkHelper.isNetworkConnected()){
            true
        }else{
            messageString.postValue("Please check your internet connection!!")
            false
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    abstract fun onCreate()
}