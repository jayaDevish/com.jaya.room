package com.jaya.bootcamp.demo

import android.app.Application
import com.jaya.bootcamp.demo.data.local.DatabaseService
import com.jaya.bootcamp.demo.data.remote.NetworkService
import com.jaya.bootcamp.demo.di.component.ApplicationComponent
import com.jaya.bootcamp.demo.di.component.DaggerApplicationComponent
import com.jaya.bootcamp.demo.di.module.ApplicationModule
import javax.inject.Inject

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var networkService: NetworkService

    @Inject
    lateinit var databaseService: DatabaseService

    override fun onCreate() {
        super.onCreate()
        getDependencies()
    }

    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }
}