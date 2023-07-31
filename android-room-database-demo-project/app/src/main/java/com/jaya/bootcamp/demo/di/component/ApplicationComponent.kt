package com.jaya.bootcamp.demo.di.component

import android.content.Context
import com.jaya.bootcamp.demo.MyApplication
import com.jaya.bootcamp.demo.data.local.DatabaseService
import com.jaya.bootcamp.demo.data.remote.NetworkService
import com.jaya.bootcamp.demo.di.ApplicationContext
import com.jaya.bootcamp.demo.di.module.ApplicationModule
import com.jaya.bootcamp.demo.utils.NetworkHelper
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getCompositeDisposable(): CompositeDisposable

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getNetworkHelper(): NetworkHelper
}
