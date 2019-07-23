package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.os.AsyncTask
import com.orange_infinity.rileywheelsimulator.data_layer.db.InnerItemsRepositoryImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.TreasureOpenerActivity
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

private const val WAITING = 1500L

class AsyncOpeningTreasure(context: TreasureOpenerActivity) : AsyncTask<Long, Unit, Unit>() {

    private val activityReference = WeakReference<TreasureOpenerActivity>(context)
    private lateinit var inventoryController: InventoryController
    private lateinit var openerController: TreasureOpenerController
    private lateinit var soundPlayer: SoundPlayer

    private var timeWaiting = 0L

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Long?) {
        timeWaiting = params.first()!!
        val activity = activityReference.get() ?: return

        initObjects(activity)
        while (true) {
            logInf(MAIN_LOGGER_TAG, "Tik Tik")
            if (openerController.isWin()) {
                logInf(MAIN_LOGGER_TAG, "Winner is ${openerController.getWinnerItem().getItemName()}")
                val winnerItem = openerController.getWinnerItem()

                soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
                return
            }
                //activity.getNextItem()

            TimeUnit.MILLISECONDS.sleep(timeWaiting)
        }

    }

    override fun onProgressUpdate(vararg values: Unit?) {
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: Unit?) {
        val activity = activityReference.get() ?: return
        initObjects(activity)
        //setSwitcher(activity)
    }

    private fun initObjects(activity: TreasureOpenerActivity) {
        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(activity.applicationContext))
        soundPlayer = SoundPlayer.getInstance(activity.applicationContext)
        openerController = TreasureOpenerController(InnerItemsRepositoryImpl.getInstance(activity.applicationContext))
    }

//    private fun setSwitcher(activity: TreasureOpenerActivity) {
//        val imgFirstItem: ImageSwitcher = activity.findViewById(R.id.imgFirstItem)
//        imgFirstItem.setFactory(activity)
//        val imgSecondItem: ImageSwitcher = activity.findViewById(R.id.imgSecondItem)
//        imgSecondItem.setFactory(activity)
//
//        val inAnimation = AlphaAnimation(0f, 1f)
//        inAnimation.duration = WAITING
//        val outAnimation = AlphaAnimation(1f, 0f)
//        outAnimation.duration = WAITING
//
//        imgFirstItem.inAnimation = inAnimation
//        imgFirstItem.outAnimation = outAnimation
//        imgSecondItem.inAnimation = inAnimation
//        imgSecondItem.outAnimation = outAnimation
//
//        firstItem = openerController.gerRandomItem(null)
//        secondItem = openerController.gerRandomItem(firstItem)
//
//        imgFirstItem.setImageDrawable(iconController.getItemIconDrawable(firstItem))
//        imgSecondItem.setImageDrawable(iconController.getItemIconDrawable(secondItem))
//    }
}