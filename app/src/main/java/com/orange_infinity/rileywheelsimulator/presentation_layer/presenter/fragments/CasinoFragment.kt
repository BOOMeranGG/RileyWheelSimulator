package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.ChartActivity
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.CoinFlipActivity
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.MainActivity
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.ProfileActivity
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.PuzzleFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.TechiesFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

const val SYMBOL_MONEY = "\uD83D\uDCB0"

class CasinoFragment : Fragment(), View.OnClickListener {

    private lateinit var tvNickname: TextView
    private lateinit var imgTechiesGame: ImageView
    private lateinit var imgPuzzle: ImageView
    private lateinit var imgCoinFlip: ImageView
    private lateinit var imgChart: ImageView
    private lateinit var imgProfile: ImageView

    companion object {
        fun newInstance(): CasinoFragment = CasinoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_casino, container, false)

        val userInfo = UserInfoController(activity,
            UserPreferencesImpl(),
            InventoryRepositoryImpl.getInstance(context?.applicationContext)
        )
        val nick = userInfo.getNickname()
        val totalCost = userInfo.getTotalItemCost()
        val itemCount = userInfo.getCountOfItems()
        val userMoney = userInfo.getUserMoney()
        val userExp = userInfo.getBattlePassExp()
        val grade = userInfo.getCurrentGrade()
        val lvl = userInfo.getCurrentLevel()

        tvNickname = v.findViewById(R.id.tvNickname)
        tvNickname.text = "$nick, total item cost = $totalCost$, item count = $itemCount, " +
                "user money = $userMoney$SYMBOL_MONEY," +
                "\nexp = $userExp, grade = $grade(${grade.countOfStars}★), level = $lvl)"

        imgTechiesGame = v.findViewById(R.id.imgTechiesGame)
        imgPuzzle = v.findViewById(R.id.imgPuzzle)
        imgCoinFlip = v.findViewById(R.id.imgCoinFlip)
        imgChart = v.findViewById(R.id.imgCrash)
        imgProfile = v.findViewById(R.id.imgProfile)
        imgTechiesGame.setOnClickListener(this)
        imgPuzzle.setOnClickListener(this)
        imgCoinFlip.setOnClickListener(this)
        imgChart.setOnClickListener(this)
        imgProfile.setOnClickListener(this)

        return v
    }

    override fun onClick(v: View) {
        when {
            v.id == R.id.imgTechiesGame -> {
                (activity as MainActivity).changeFragment(TechiesFragment.newInstance())
                logInf(MAIN_LOGGER_TAG, "GO to Techies Game")
            }
            v.id == R.id.imgPuzzle -> {
                (activity as MainActivity).changeFragment(PuzzleFragment.newInstance())
                logInf(MAIN_LOGGER_TAG, "GO to 3on3 game(puzzle fragment)")
            }
            v.id == R.id.imgCoinFlip -> {
                val intent = Intent(activity, CoinFlipActivity::class.java)
                startActivity(intent)
                logInf(MAIN_LOGGER_TAG, "Go to CoinFlipActivity")
            }
            v.id == R.id.imgCrash -> {
                val intent = Intent(activity, ChartActivity::class.java)
                startActivity(intent)
                logInf(MAIN_LOGGER_TAG, "Go to ChartActivity")
            }
            v.id == R.id.imgProfile -> {
                val intent = Intent(activity, ProfileActivity::class.java)
                startActivity(intent)
                logInf(MAIN_LOGGER_TAG, "Go to ProfileActivity")
            }
        }
    }
}