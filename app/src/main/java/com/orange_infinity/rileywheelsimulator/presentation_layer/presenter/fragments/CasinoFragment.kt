package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.MainActivity
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.TechiesFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoSaver
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class CasinoFragment : Fragment(), View.OnClickListener {

    private lateinit var tvNickname: TextView
    private lateinit var imgTechiesGame: ImageView

    companion object {
        fun newInstance(): CasinoFragment = CasinoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.casino_fragment, container, false)

        val infoSaver = UserInfoSaver(activity,
            UserPreferencesImpl()
        )
        val nick = infoSaver.getNickname()
        val totalCost = infoSaver.getTotalItemCost()
        val itemCount = infoSaver.getCountOfItems()

        tvNickname = v.findViewById(R.id.tvNickname)
        tvNickname.text = "$nick, total item cost = $totalCost$, item count = $itemCount"

        imgTechiesGame = v.findViewById(R.id.imgTechiesGame)
        imgTechiesGame.setOnClickListener(this)

        return v
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgTechiesGame) {
            (activity as MainActivity).changeFragment(TechiesFragment.newInstance())
            logInf(MAIN_LOGGER_TAG, "GO to Techies Game")
        }
    }
}