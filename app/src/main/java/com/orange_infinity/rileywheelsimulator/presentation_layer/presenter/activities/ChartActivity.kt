package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.ChartControllerFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.ChartFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_MINES_BOOM
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class ChartActivity : AppCompatActivity(), ChartControllerFragment.OnControllerButtonClicked, ChartFragment.OnLoseGame {

    private lateinit var userInfoController: UserInfoController
    private lateinit var layoutChart: FrameLayout
    private lateinit var layoutInfo: FrameLayout
    private lateinit var soundPlayer: SoundPlayer
    private var currentBet: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        layoutChart = findViewById(R.id.layoutChart)
        layoutInfo = findViewById(R.id.layoutInfo)
        soundPlayer = SoundPlayer.getInstance(this)
        userInfoController = UserInfoController(
            this, UserPreferencesImpl(), InventoryRepositoryImpl.getInstance(applicationContext)
        )

        createChartFragment()
        createChartControllerFragment()
    }

    private fun createChartFragment() {
        val fmChart = supportFragmentManager
        var chartFragment = fmChart.findFragmentById(R.id.layoutChart)

        if (chartFragment == null) {
            chartFragment = ChartFragment.newInstance()
            fmChart.beginTransaction()
                .add(R.id.layoutChart, chartFragment)
                .commit()
        }
    }

    private fun createChartControllerFragment() {
        val fmController = supportFragmentManager
        var controllerFragment = fmController.findFragmentById(R.id.layoutInfo)

        if (controllerFragment == null) {
            controllerFragment = ChartControllerFragment.newInstance()
            fmController.beginTransaction()
                .add(R.id.layoutInfo, controllerFragment)
                .commit()
        }
    }

    private fun changeChartFragment() {
        val fmChart = supportFragmentManager
        fmChart.beginTransaction()
            .replace(R.id.layoutChart, ChartFragment.newInstance())
            .commit()
    }

    override fun onPlaceBet(betMoney: Float) {
        val chartFragment = supportFragmentManager.findFragmentById(R.id.layoutChart) as ChartFragment
        chartFragment.startChart()
        currentBet = betMoney
        userInfoController.changeUserMoney((currentBet * -1))
        logInf(MAIN_LOGGER_TAG, "Place bet: $betMoney$")
    }

    override fun onStopBet() {
        val chartFragment = supportFragmentManager.findFragmentById(R.id.layoutChart) as ChartFragment
        chartFragment.setStopped(true)
        val winningPrize = chartFragment.currentMultiplier * currentBet
        userInfoController.changeUserMoney(winningPrize.toFloat())

        soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
        Toast.makeText(this, "You are WINNER! +$winningPrize$", Toast.LENGTH_LONG).show()
        logInf(MAIN_LOGGER_TAG, "Stop bet, win: ${(Math.round(winningPrize * 100.0) / 100.0)}$")
    }

    override fun onContinue() {
        changeChartFragment()
        logInf(MAIN_LOGGER_TAG, "Prepare next game(create new chartFragment)")
    }

    override fun loseGame() {
        val controllerFragment = supportFragmentManager.findFragmentById(R.id.layoutInfo) as ChartControllerFragment
        controllerFragment.onLose()
        soundPlayer.standardPlay(SOUND_MINES_BOOM)
        logInf(MAIN_LOGGER_TAG, "Defeat")
    }
}
