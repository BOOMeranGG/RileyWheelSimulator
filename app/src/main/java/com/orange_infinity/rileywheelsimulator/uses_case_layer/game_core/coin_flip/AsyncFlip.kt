package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import android.os.AsyncTask
import android.widget.ImageView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.CoinFlipRoomActivity
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class AsyncFlip(context: CoinFlipRoomActivity) : AsyncTask<Unit, Unit, Unit>() {

    private val activityReference = WeakReference<CoinFlipRoomActivity>(context)
    private var mult = 1
    private var isCanSeeFirstCoin = true

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Unit?) {
        while (true) {
            TimeUnit.MILLISECONDS.sleep(10)
            publishProgress()
        }
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
        imgCoin.rotationX += 1 * mult
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
    }
}