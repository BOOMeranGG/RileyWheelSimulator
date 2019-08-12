package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.SYMBOL_MONEY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController

class ProfileActivity : AppCompatActivity() {

    private lateinit var userInfo: UserInfoController
    private lateinit var iconController: IconController
    private lateinit var tvNickname: TextView
    private lateinit var tvLevel: TextView
    private lateinit var tvMoney: TextView
    private lateinit var imgGrade: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profiler)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        userInfo = UserInfoController(
            this,
            UserPreferencesImpl(),
            InventoryRepositoryImpl.getInstance(applicationContext)
        )

        iconController = IconController.getInstance(applicationContext)
        tvNickname = findViewById(R.id.tvNickname)
        tvLevel = findViewById(R.id.tvLevel)
        tvMoney = findViewById(R.id.tvMoney)
        imgGrade = findViewById(R.id.imgGrade)

        createViews()
    }

    @SuppressLint("SetTextI18n")
    private fun createViews() {
        val nick = userInfo.getNickname()
        //val totalCost = userInfo.getTotalItemCost()
        //val itemCount = userInfo.getCountOfItems()
        val userMoney = userInfo.getUserMoney()
        //val userExp = userInfo.getBattlePassExp()
        val grade = userInfo.getCurrentGrade()
        val lvl = userInfo.getCurrentLevel()

        tvNickname.text = nick
        tvLevel.text = "Compendium level: $lvl"
        tvMoney.text = "Balance:  ${(Math.round(userMoney * 100.0) / 100.0)}$SYMBOL_MONEY"
        imgGrade.setImageDrawable(iconController.getGradeDrawable(grade))
    }
}