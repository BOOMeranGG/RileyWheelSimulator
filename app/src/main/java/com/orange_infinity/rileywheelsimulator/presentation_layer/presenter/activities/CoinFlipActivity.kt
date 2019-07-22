package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.CoinFlipUsersFragment
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class CoinFlipActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fm: FragmentManager
    private lateinit var btnCreateCoinFlip: Button
    private lateinit var btnCasino: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_flip)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        initView()

        if (!isFinishing) {
            fm = supportFragmentManager
            var fragment = fm.findFragmentById(R.id.mainLayout)

            if (fragment == null) {
                fragment = CoinFlipUsersFragment.newInstance()
                fm.beginTransaction()
                    .add(R.id.mainLayout, fragment)
                    .commit()
            }
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnCreateCoinFlip) {
            val intent = Intent(this, ChooseItemCoinPickActivity::class.java)
            startActivity(intent)
            logInf(MAIN_LOGGER_TAG, "Button Create CoinFlip pressed, create ChooseItemCoinPickActivity")
        } else {

        }
    }

    private fun initView() {
        btnCreateCoinFlip = findViewById(R.id.btnCreateCoinFlip)
        btnCreateCoinFlip.setOnClickListener(this)
        btnCasino = findViewById(R.id.btnCasino)
        btnCasino.setOnClickListener {
            finish()
            logInf(MAIN_LOGGER_TAG, "Back from CoinFlip")
        }
    }
}
