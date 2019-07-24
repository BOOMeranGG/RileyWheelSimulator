package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.CoinFlipUsersFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.BotAccount
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val COUNT_OF_BOTS = 3

class CoinFlipActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var fm: FragmentManager
    private lateinit var btnCreateCoinFlip: Button
    private lateinit var btnCasino: Button
    private lateinit var leftAccountLayout: LinearLayout
    private lateinit var rightAccountLayout: LinearLayout

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
        createBots()
    }

    private fun createBots() {
        val bot = BotAccount(playerTeamName = null, context = this)
        val innerLayout = LinearLayout(this)
        val layoutParams = FrameLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        innerLayout.weightSum = 1f
        innerLayout.orientation = LinearLayout.VERTICAL

        //val viewInclude = resources.getLayout(R.layout.include_bot)
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

        leftAccountLayout = findViewById(R.id.leftAccountLayout)
        rightAccountLayout = findViewById(R.id.rightAccountLayout)
    }
}
