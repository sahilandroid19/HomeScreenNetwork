package com.example.hmn.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hmn.MyApplication
import com.example.hmn.di.components.ActivityComponent
import com.example.hmn.di.components.DaggerActivityComponent
import com.example.hmn.di.modules.ActivityModule
import com.example.hmn.utils.common.Toaster
import javax.inject.Inject

/*
Base class for all Activities containing common code
 */
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(){

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setUpObservers()
        if(viewModel.checkInternetConnection()) viewModel.onCreate()
        setUpView(savedInstanceState)
    }

    /*
    Function to set observers to live data's
     */
    protected open fun setUpObservers() {
        viewModel.messageString.observe(this, Observer {
          Toaster.showToast(this, it)
        })
    }

    protected abstract fun setUpView(savedInstanceState: Bundle?)

    @LayoutRes
    protected abstract fun provideLayoutId() : Int

    /*
    Function to building up Dagger activity component-common to all
     */
    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as MyApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)
}