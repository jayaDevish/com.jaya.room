package com.jaya.bootcamp.demo.di.component

import com.jaya.bootcamp.demo.di.module.FragmentModule
import com.jaya.bootcamp.demo.di.FragmentScope
import com.jaya.bootcamp.demo.ui.home.HomeFragment

import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: HomeFragment)
}
