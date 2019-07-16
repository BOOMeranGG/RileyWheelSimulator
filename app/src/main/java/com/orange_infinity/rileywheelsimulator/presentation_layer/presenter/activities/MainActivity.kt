package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.RileyWheelFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.TopButtonFragment
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class MainActivity : AppCompatActivity() {

    lateinit var fm: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_container)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragmentButtonContainer)

        if (fragment == null) {
            fragment = getTopButtonFragment()
            fm.beginTransaction()
                .add(R.id.fragmentButtonContainer, fragment)
                .commit()
        }
        createRileyWheelFragment()
    }
    //TODO("Возможно вылетает из-за этого")
    fun changeFragment(newFragment: Fragment) {
        fm.beginTransaction()
            .replace(R.id.fragmentMainContainer, newFragment)
            .commit()
        logInf(MAIN_LOGGER_TAG, "Replace fragment to $newFragment")
    }
    //TODO("Или из-за этого")
    private fun createRileyWheelFragment() {
        var fragment = fm.findFragmentById(R.id.fragmentMainContainer)

        if (fragment == null) {
            fragment = getRileyWheelFragment()
            fm.beginTransaction()
                .add(R.id.fragmentMainContainer, fragment)
                .commit()
        }
    }

    private fun getTopButtonFragment(): Fragment =
        TopButtonFragment.newInstance()

    private fun getRileyWheelFragment(): Fragment =
        RileyWheelFragment.newInstance()
}