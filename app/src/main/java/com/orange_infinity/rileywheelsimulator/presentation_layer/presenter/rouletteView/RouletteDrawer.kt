package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.rouletteView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.baseView.BaseDrawer
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.convertToPx
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

private const val COUNT_OF_VISIBLE_ITEMS = 4
private const val DRAWABLE_CLOSE_ITEM = R.drawable.dota2_logo
private const val TIME_TO_SLEEP_MILLISECONDS = 16L

class RouletteDrawer(
    val view: RouletteView,
    private val iconController: IconController
) : BaseDrawer(view) {

    private lateinit var mainCanvas: Canvas
    private var fullBitmap: Bitmap? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var sizeOfOneItem = 0
    private var timer = Timer()
    private var timerTask = MoveViewTimerTask()
    private var frameMovingSize = 0
    private var indent = 0
    private var indentLength = 0
    private var x = 0
    private var y = 0

    fun drawStatic(canvas: Canvas, context: Context) {
        x = view.width / 2
        y = view.height / 2

        sizeOfOneItem = x / COUNT_OF_VISIBLE_ITEMS

        for (i in 0 until COUNT_OF_VISIBLE_ITEMS) {
            val bitmap = getScaledSideBitmap(context, sizeOfOneItem, sizeOfOneItem, DRAWABLE_CLOSE_ITEM)
            canvas.drawBitmap(bitmap, (i * sizeOfOneItem * 2).toFloat(), 0f, paint)
        }
    }

    fun stopMove() {
        timer.cancel()
    }

    fun startMove(itemList: List<Item>, canvas: Canvas) {
        if (fullBitmap == null) {
            fullBitmap = createActionDrawable(itemList)
            mainCanvas = canvas
        }
        if (indent == 0) {
            startDrawingAction()
        } else {
            drawFrame(indent)
        }
        drawLine()
    }

    private fun startDrawingAction() {
        frameMovingSize = sizeOfOneItem * 4 / 60
        timer.schedule(timerTask, 0L, TIME_TO_SLEEP_MILLISECONDS)
    }

    private fun drawFrame(indent: Int) {
        mainCanvas.drawBitmap(fullBitmap, (indent * -1).toFloat(), 0f, paint)
    }

    private fun drawLine() {
    }

    private fun createActionDrawable(actionList: List<Item>): Bitmap {
        val fullBitmap = Bitmap.createBitmap(
            sizeOfOneItem * actionList.size * 2,
            sizeOfOneItem * 2,
            Bitmap.Config.ARGB_8888
        )
        val canvasDrawable = Canvas(fullBitmap)
        indentLength = fullBitmap.width

        for (i in 0 until actionList.size) {
            val itemDrawable = iconController.getItemIconDrawable(actionList[i])
            val itemBitmap = (itemDrawable as BitmapDrawable).bitmap
            val itemScaledBitmap = Bitmap.createScaledBitmap(itemBitmap, sizeOfOneItem * 2, sizeOfOneItem * 2, false)
            canvasDrawable.drawBitmap(itemScaledBitmap, (i * sizeOfOneItem * 2).toFloat(), 0f, paint)
        }
        return fullBitmap
    }

    private fun refreshTimer() {
        timer.cancel()
        timer = Timer()
        timerTask = MoveViewTimerTask()
    }

    private inner class MoveViewTimerTask : TimerTask() {

        override fun run() {
            indent += frameMovingSize
            view.getActivity()?.runOnUiThread {
                view.invalidate()
                logInf(MAIN_LOGGER_TAG, "indent = $indent, indentPx = ${convertToPx(indent, view.resources)} frameMovingSize = $frameMovingSize")
            }
            if (convertToPx(indent, view.resources) >= indentLength) {
                refreshTimer()
            }
        }
    }
}