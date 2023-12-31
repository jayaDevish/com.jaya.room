package com.jaya.bootcamp.demo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jaya.bootcamp.demo.data.local.DatabaseService
import com.jaya.bootcamp.demo.data.local.entity.Address
import com.jaya.bootcamp.demo.data.local.entity.User
import com.jaya.bootcamp.demo.data.remote.NetworkService
import com.jaya.bootcamp.demo.di.ActivityScope
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScope
class MainViewModel @Inject constructor(
        private val compositeDisposable: CompositeDisposable,
        private val databaseService: DatabaseService,
        private val networkService: NetworkService) {

    companion object {
        const val TAG = "MainViewModel"
    }

    val user = MutableLiveData<User>()
    val allUser = MutableLiveData<List<User>>()
    val allAddress = MutableLiveData<List<Address>>()

    private var users: List<User> = emptyList()
    private var addresses: List<Address> = ArrayList()

    init {
        // add dummy users in the database
        compositeDisposable.add(
                databaseService.userDao()
                        .count()
                        .flatMap {
                            if (it == 0)
                                databaseService
                                        .addressDao()
                                        .insertMany(
                                                Address(city = "Delhi", country = "India", code = 1),
                                                Address(city = "New York", country = "US", code = 2),
                                                Address(city = "Berlin", country = "Germany", code = 3),
                                                Address(city = "London", country = "UK", code = 4),
                                                Address(city = "Bangalore", country = "India", code = 5),
                                                Address(city = "Barcelona", country = "Spain", code = 6)
                                        )
                                        .flatMap { addressIds ->
                                            databaseService
                                                    .userDao()
                                                    .insertMany(
                                                            User(name = "Test 1", addressId = addressIds[0], dateOfBirth = Date(959684579)),
                                                            User(name = "Test 1", addressId = addressIds[1], dateOfBirth = Date(959684579)),
                                                            User(name = "Test 1", addressId = addressIds[2], dateOfBirth = Date(959684579)),
                                                            User(name = "Test 1", addressId = addressIds[3], dateOfBirth = Date(959684579)),
                                                            User(name = "Test 1", addressId = addressIds[4], dateOfBirth = Date(959684579)),
                                                            User(name = "Test 1", addressId = addressIds[5], dateOfBirth = Date(959684579))
                                                    )
                                        }

                            else Single.just(0)
                        }
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    Log.d(TAG, "Users present in db $it")
                                },
                                {
                                    Log.d(TAG, it.toString())
                                }
                        )
        )

    }

    fun getAllUser() {
        compositeDisposable.add(
                databaseService.userDao()
                        .getAllUsers()
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    users = it
                                    allUser.postValue(it)
                                },
                                {
                                    Log.d(TAG, it.toString())
                                }
                        )
        )
    }

    fun getAllAddress() {
        compositeDisposable.add(
                databaseService.addressDao()
                        .getAllAddresses()
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    addresses = it
                                    allAddress.postValue(it)
                                },
                                {
                                    Log.d(TAG, it.toString())
                                }
                        )
        )
    }


    fun getUser(id: Long) {
        compositeDisposable.add(
                databaseService.userDao()
                        .getUserById(id)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                {
                                    user.postValue(it)
                                },
                                {
                                    Log.d(TAG, it.toString())
                                }
                        )
        )
    }

    fun deleteUser() {
        if (users.isNotEmpty())
            compositeDisposable.add(
                    databaseService.userDao()
                            .delete(users[0])
                            .flatMap {
                                databaseService.userDao().getAllUsers()
                            }
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        allUser.postValue(it)
                                    },
                                    {
                                        Log.d(TAG, it.toString())
                                    }
                            )
            )
    }

    fun deleteAddress() {
        if (addresses.isNotEmpty())
            compositeDisposable.add(
                    databaseService.addressDao()
                            .delete(addresses[0])
                            .flatMap {
                                databaseService.userDao().getAllUsers()
                            }
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        allUser.postValue(it)
                                    },
                                    {
                                        Log.d(TAG, it.toString())
                                    }
                            )
            )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}
