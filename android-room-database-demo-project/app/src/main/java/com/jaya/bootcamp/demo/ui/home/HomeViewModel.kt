package com.jaya.bootcamp.demo.ui.home

import com.jaya.bootcamp.demo.data.local.DatabaseService
import com.jaya.bootcamp.demo.data.remote.NetworkService
import com.jaya.bootcamp.demo.di.FragmentScope
import com.jaya.bootcamp.demo.utils.NetworkHelper

import javax.inject.Inject

@FragmentScope
class HomeViewModel @Inject constructor(
        private val databaseService: DatabaseService,
        private val networkService: NetworkService,
        private val networkHelper: NetworkHelper) {

    val someData: String
        get() = "HomeViewModel"
}
