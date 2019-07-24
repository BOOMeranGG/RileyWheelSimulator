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
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.CoinFlipActivity
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.MainActivity
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.PuzzleFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.TechiesFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class CasinoFragment : Fragment(), View.OnClickListener {

    private lateinit var tvNickname: TextView
    private lateinit var imgTechiesGame: ImageView
    private lateinit var imgPuzzle: ImageView
    private lateinit var imgChart: ImageView

    companion object {
        fun newInstance(): CasinoFragment = CasinoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_casino, container, false)

        val infoSaver = UserInfoController(activity,
            UserPreferencesImpl(),
            InventoryRepositoryImpl.getInstance(context?.applicationContext)
        )
        val nick = infoSaver.getNickname()
        val totalCost = infoSaver.getTotalItemCost()
        val itemCount = infoSaver.getCountOfItems()
        val userMoney = infoSaver.getUserMoney()

        tvNickname = v.findViewById(R.id.tvNickname)
        tvNickname.text = "$nick, total item money = $totalCost$, item count = $itemCount, user money = $userMoney$"

        imgTechiesGame = v.findViewById(R.id.imgTechiesGame)
        imgPuzzle = v.findViewById(R.id.imgPuzzle)
        imgChart = v.findViewById(R.id.imgChart)
        imgTechiesGame.setOnClickListener(this)
        imgPuzzle.setOnClickListener(this)
        imgChart.setOnClickListener(this)

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
            v.id == R.id.imgChart -> {
                val intent = Intent(activity, CoinFlipActivity::class.java)
                startActivity(intent)
                logInf(MAIN_LOGGER_TAG, "Go to CoinFlipActivity")
            }
        }
    }
}