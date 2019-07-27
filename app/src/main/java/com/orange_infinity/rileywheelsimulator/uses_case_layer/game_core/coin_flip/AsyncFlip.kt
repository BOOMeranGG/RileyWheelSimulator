package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import android.os.AsyncTask
import android.widget.ImageView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.CoinFlipRoomActivity
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

private const val MAX_TIME_MILLISECOND = 5000L
private const val TIME_TO_SLEEP = 10L

class AsyncFlip(context: CoinFlipRoomActivity) :
    AsyncTask<BotAccount, Unit, Unit>() {

    private val activityReference = WeakReference<CoinFlipRoomActivity>(context)

    private lateinit var gameController: GameController
    private var gameHost: GameResultListener = context
    private var mult = 1
    private var isCanSeeFirstCoin = true
    private var isFlipEnd = false
    private var isPlayerWinner = false

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: BotAccount?) {
        val botAccount = params.first() ?: return
        gameController = GameController(botAccount)
        isPlayerWinner = gameController.isPlayerWinner()
        logInf(MAIN_LOGGER_TAG, " Is player winner = $isPlayerWinner")

        controlWaiting()
    }

    override fun onProgressUpdate(vararg values: Unit?) {
        val activity = activityReference.get() ?: return
        val imgFirstPlayer = activity.findViewById<ImageView>(R.id.imgFirstPlayer)
        val imgSecondPlayer = activity.findViewById<ImageView>(R.id.imgSecondPlayer)
        val imgCoin = activity.findViewById<ImageView>(R.id.imgCoin)

        if (imgCoin.rotationX == 0f)
            mult = 1
        if (imgCoin.rotationX == 90f)
            mult = -1
        if (imgCoin.rotationX == 90f) {
            if (isCanSeeFirstCoin) {
                imgCoin.setImageDrawable(imgFirstPlayer.drawable)
            } else {
                imgCoin.setImageDrawable(imgSecondPlayer.drawable)
            }

            isCanSeeFirstCoin = !isCanSeeFirstCoin
        }
        imgCoin.rotationX += 2 * mult
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        if (isPlayerWinner) {
            gameHost.playerWinner()
        } else {
            gameHost.botWinner()
        }
    }

    private fun controlWaiting() {
        var currentTime = 0L
        val activity = activityReference.get() ?: return
        val imgCoin = activity.findViewById<ImageView>(R.id.imgCoin)
        val imgWinner = if (isPlayerWinner) {
            activity.findViewById<ImageView>(R.id.imgFirstPlayer)
        } else {
            activity.findViewById(R.id.imgSecondPlayer)
        }

        while (true) {
            if (isCancelled)
                return
            TimeUnit.MILLISECONDS.sleep(TIME_TO_SLEEP)
            publishProgress()
            currentTime += TIME_TO_SLEEP
            if (currentTime >= MAX_TIME_MILLISECOND && imgCoin.rotationX == 0f
                && imgWinner.drawable == imgCoin.drawable
            ) {
                isFlipEnd = true
                break
            }
        }
    }

    interface GameResultListener {

        fun playerWinner()

        fun botWinner()
    }
}