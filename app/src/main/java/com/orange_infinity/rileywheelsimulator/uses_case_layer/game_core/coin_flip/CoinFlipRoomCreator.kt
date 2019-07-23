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

class CoinFlipRoomCreator(val context: CoinFlipRoomActivity, playerTeam: String) {

    private lateinit var botWaitingController: BotWaitingController
    private val botAccount = BotAccount(playerTeamName = playerTeam)
    private var chance = 50.0f

    fun playerCreateGame(playerMoney: Float) {
        val second = 2 + (Math.random() * 3).toLong()
        val millisecond = (Math.random() * 999).toLong()
        val waitingMilli = second * 1000 + millisecond
        logInf(MAIN_LOGGER_TAG, "Time set to wait: $waitingMilli ms")

        chance = calculateChance(playerMoney, botAccount.money)

        botWaitingController = BotWaitingController(context, waitingMilli, botAccount)
        botWaitingController.execute()
    }


    private fun calculateChance(playerMoney: Float, botMoney: Float): Float {
        return 50.0f
    }

    private class BotWaitingController(
        context: CoinFlipRoomActivity,
        val waiting: Long,
        val botAccount: BotAccount
    ) : AsyncTask<Unit, Unit, Unit>() {

        override fun onPreExecute() {
            val activity = activityReference.get() ?: return
            val btnStart = activity.findViewById<Button>(R.id.btnStart)
            btnStart.isClickable = false
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

            progressWaiting.visibility = ProgressBar.INVISIBLE
            imgSecondPlayer.visibility = ImageView.VISIBLE
            imgCoin.visibility = ImageView.VISIBLE
            imgCoin.setImageResource(botAccount.imgTeamId)
            imgSecondPlayer.setImageResource(botAccount.imgTeamId)
            btnStart.isClickable = true

            createTextBotInfo(activity)
        }

        @SuppressLint("SetTextI18n")
        private fun createTextBotInfo(activity: CoinFlipRoomActivity) {
            activity.findViewById<TextView>(R.id.tvName2).text = botAccount.nickname
            activity.findViewById<TextView>(R.id.tvCountOfItems2).text = "${botAccount.countOfItems} ITEMS"
            activity.findViewById<TextView>(R.id.tvValue2).text = "${botAccount.money}$"
            activity.findViewById<TextView>(R.id.tvChance2).text = "${botAccount.chance}%"
        }
    }
}