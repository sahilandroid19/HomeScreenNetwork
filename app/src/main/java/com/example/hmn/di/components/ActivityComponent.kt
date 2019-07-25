package com.example.hmn.di.components

import com.example.hmn.di.modules.ActivityModule
import com.example.hmn.di.scope.ActivityScope
import com.example.hmn.ui.location.LocationActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(locationActivity: LocationActivity)

}