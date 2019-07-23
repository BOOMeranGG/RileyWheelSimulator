package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.widget.*
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.CoinFlipRoomActivity
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class CoinFlipRoomCreator(val context: CoinFlipRoomActivity, playerTeam: String, playerMoney: Float) {

    private lateinit var botWaitingController: BotWaitingController
    private val botAccount = BotAccount(playerMoney = playerMoney, playerTeamName = playerTeam, context = context)
    private var chance = 50.0f

    fun joinBotInRoom(playerMoney: Float): BotAccount {
        val second = 2 + (Math.random() * 3).toLong()
        val millisecond = (Math.random() * 999).toLong()
        val waitingMilli = second * 1000 + millisecond
        logInf(MAIN_LOGGER_TAG, "Time set to wait: $waitingMilli ms")

        //chance = calculateChance(playerMoney, botAccount.money)

        botWaitingController = BotWaitingController(context, waitingMilli, botAccount, playerMoney)
        botWaitingController.execute()
        
        return botAccount
    }

    private class BotWaitingController(
        context: CoinFlipRoomActivity,
        val waiting: Long,
        val botAccount: BotAccount,
        val playerMoney: Float
    ) : AsyncTask<Unit, Unit, Unit>() {

        override fun onPreExecute() {
            val activity = activityReference.get() ?: return
            val btnStart = activity.findViewById<Button>(R.id.btnStart)
            btnStart.isClickable = false
            createPlayerTextInfo(activity)
        }

        private val activityReference = WeakReference<CoinFlipRoomActivity>(context)

        override fun doInBackground(vararg params: Unit?) {
            TimeUnit.MILLISECONDS.sleep(waiting)
        }

        override fun onPostExecute(result: Unit?) {
            val activity = activityReference.get() ?: return
            val progressWaiting = activity.findViewById<ProgressBar>(R.id.prWaitBot)
            val imgSecondPlayer = activity.findViewById<ImageView>(R.id.imgSecondPlayer)
            val imgCoin = activity.findViewById<ImageView>(R.id.imgCoin)
            val btnStart = activity.findViewById<Button>(R.id.btnStart)
            
            val tvChance = activity.findViewById<TextView>(R.id.tvChance)
            tvChance.text = "${100 - botAccount.chance}%"

            activity.findViewById<TextView>(R.id.tvInfoProgress).visibility = TextView.INVISIBLE

            progressWaiting.visibility = ProgressBar.INVISIBLE
            imgSecondPlayer.visibility = ImageView.VISIBLE
            imgCoin.visibility = ImageView.VISIBLE
            imgCoin.setImageResource(botAccount.imgTeamId)
            imgSecondPlayer.setImageResource(botAccount.imgTeamId)
            btnStart.isClickable = true

            createBotTextInfo(activity)
        }

        @SuppressLint("SetTextI18n")
        private fun createPlayerTextInfo(activity: CoinFlipRoomActivity) {
            val tvValue = activity.findViewById<TextView>(R.id.tvValue)
            tvValue.text = "$playerMoney$"
        }

        @SuppressLint("SetTextI18n")
        private fun createBotTextInfo(activity: CoinFlipRoomActivity) {
            val nick = activity.findViewById<TextView>(R.id.tvName2)
            val countOfItems = activity.findViewById<TextView>(R.id.tvCountOfItems2)
            val accMoney = activity.findViewById<TextView>(R.id.tvValue2)
            val chance = activity.findViewById<TextView>(R.id.tvChance2)

            nick.visibility = TextView.VISIBLE
            countOfItems.visibility = TextView.VISIBLE
            accMoney.visibility = TextView.VISIBLE
            chance.visibility = TextView.VISIBLE

            nick.text = botAccount.nickname
            countOfItems.text = "${botAccount.countOfItems} ITEMS"
            accMoney.text = "${botAccount.money}$"
            chance.text = "${botAccount.chance}%"
        }
    }
}