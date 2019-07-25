package com.example.hmn.ui.location

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.hmn.R
import com.example.hmn.di.components.ActivityComponent
import com.example.hmn.ui.base.BaseActivity
import com.example.hmn.utils.location.LocationService
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : BaseActivity<LocationViewModel>() {

    override fun setUpView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        ContextCompat.startForegroundService(this,
            Intent(this, LocationService::class.java))
    }

    override fun provideLayoutId() = R.layout.activity_location

    override fun injectDependencies(activityComponent: ActivityComponent) =
        activityComponent.inject(this)


    @SuppressLint("SetTextI18n")
    override fun setUpObservers() {
        super.setUpObservers()
        viewModel.getLocationData()
            .observe(this, Observer {
                findViewById<TextView>(R.id.location)
                    .text = "Latitude: ${it.latitude}, \n" +
                            "Longitude: ${it.longitude}"
            })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}
