package com.jaya.bootcamp.demo.ui.main

import android.os.Bundle
import android.widget.TextView

import com.jaya.bootcamp.demo.MyApplication
import com.jaya.bootcamp.demo.R
import com.jaya.bootcamp.demo.di.component.DaggerActivityComponent
import com.jaya.bootcamp.demo.di.module.ActivityModule
import com.jaya.bootcamp.demo.ui.home.HomeFragment

import javax.inject.Inject

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        getDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvData = findViewById<TextView>(R.id.tv_message)

        addHomeFragment()

        viewModel.user.observe(this, Observer {
            tvData.text = it.toString()
        })

        viewModel.allUser.observe(this, Observer {
            tvData.text = it.toString()
        })
    }

    private fun addHomeFragment() {
        if (supportFragmentManager.findFragmentByTag(HomeFragment.TAG) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container_fragment, HomeFragment.newInstance(), HomeFragment.TAG)
                    .commit()
        }
    }

    private fun getDependencies() {
        DaggerActivityComponent
                .builder()
                .applicationComponent((application as MyApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllAddress()
        viewModel.getAllUser()
    }

    override fun onStop() {
        super.onStop()
//        viewModel.deleteUser()
        viewModel.deleteAddress()
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }
}
