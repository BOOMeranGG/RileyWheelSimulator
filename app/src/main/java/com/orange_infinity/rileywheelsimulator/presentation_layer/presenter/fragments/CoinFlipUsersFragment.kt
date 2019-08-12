package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.percent.PercentRelativeLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.BotAccount
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.BotCreator

class CoinFlipUsersFragment : Fragment() {

    private lateinit var layoutUsers: PercentRelativeLayout
    private var botIncludes = mutableListOf<PercentRelativeLayout>()
    private var bots = mutableListOf<BotAccount>()
    private lateinit var botCreator: BotCreator

    companion object {
        fun newInstance() = CoinFlipUsersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_coin_flip_users, container, false)

        layoutUsers = v.findViewById(R.id.layoutUsers)
        botCreator = BotCreator(context!!)
        initUserIncludes(v)
        createBots()
        setBots()

        return v
    }

    private fun createBots() {
        for (botLayout in botIncludes) {
            val bot = BotAccount(10f, null, context!!)
            bots.add(bot)
        }
    }

    private fun initUserIncludes(v: View) {
        botIncludes.add(v.findViewById(R.id.bot1))
        botIncludes.add(v.findViewById(R.id.bot2))
        botIncludes.add(v.findViewById(R.id.bot3))
        botIncludes.add(v.findViewById(R.id.bot4))
        botIncludes.add(v.findViewById(R.id.bot5))
        botIncludes.add(v.findViewById(R.id.bot6))
    }

    @SuppressLint("SetTextI18n")
    private fun setBots() {
        for (i in 0 until botIncludes.size) {
            val botInclude = botIncludes[i]
            val imgTeam = botInclude.findViewById<ImageView>(R.id.imgTeam)
            val tvCountOfItems = botInclude.findViewById<TextView>(R.id.tvCountOfItems)
            val tvValue = botInclude.findViewById<TextView>(R.id.tvValue)
            val tvBotName = botInclude.findViewById<TextView>(R.id.tvBotName)
            val btnJoin = botInclude.findViewById<Button>(R.id.btnJoin)

            val bot = bots[i]
            imgTeam.setImageResource(bot.imgTeamId)
            tvCountOfItems.text = "${bot.countOfItems} ITEMS"
            tvValue.text = "${Math.round(bot.money * 100.0) / 100.0}$"
            tvBotName.text = bot.nickname

            btnJoin.setOnClickListener {
                Toast.makeText(
                    context, "Sorry, now it's doesn't work in your devise. Click \"Create CoinFlip\"",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}