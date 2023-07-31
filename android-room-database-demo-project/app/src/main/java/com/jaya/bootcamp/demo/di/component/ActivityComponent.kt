package com.jaya.bootcamp.demo.di.component

import com.jaya.bootcamp.demo.di.module.ActivityModule
import com.jaya.bootcamp.demo.di.ActivityScope
import com.jaya.bootcamp.demo.ui.main.MainActivity

import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}
