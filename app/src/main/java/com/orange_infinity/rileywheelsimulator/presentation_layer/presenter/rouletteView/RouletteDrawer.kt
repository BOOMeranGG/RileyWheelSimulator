package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.rouletteView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
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
import java.util.concurrent.TimeUnit

private const val COUNT_OF_VISIBLE_ITEMS = 4
private const val DRAWABLE_CLOSE_ITEM = R.drawable.dota2_logo
private const val TIME_TO_SLEEP_MILLISECONDS = 16L

class RouletteDrawer(
    val view: RouletteView,
    private val iconController: IconController
) : BaseDrawer(view) {

    private var fullBitmap: Bitmap? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var sizeOfOneItem = 0
    private var timer = Timer()
    private var timerTask = MoveViewTimerTask()
    private var frameStandardMovingSize = 0
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
        refreshTimer()
    }

    fun startMove(canvas: Canvas, itemList: List<Item>) {
        if (fullBitmap == null) {
            fullBitmap = createActionDrawable(itemList)
        }
        if (indent == 0) {
            startDrawingAction()
        } else {
            drawFrame(canvas, indent)
        }
        drawVerticalLine(canvas)
    }

    private fun startDrawingAction() {
        frameStandardMovingSize = sizeOfOneItem * 4 / 60
        timer.schedule(timerTask, 0L, TIME_TO_SLEEP_MILLISECONDS)
    }

    private fun drawFrame(canvas: Canvas, indent: Int) {
        canvas.drawBitmap(fullBitmap, (indent * -1).toFloat(), 0f, paint)
    }

    private fun drawVerticalLine(canvas: Canvas) {
        val middleX = canvas.width / 2
        val lineWidth = 3

        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.LTGRAY

        canvas.drawRect(
            (middleX - lineWidth).toFloat(),
            0f,
            (middleX + lineWidth).toFloat(),
            convertToPx(sizeOfOneItem, view.resources).toFloat(),
            paint
        )
    }

    private fun createActionDrawable(actionList: List<Item>): Bitmap {
        val fullBitmap = Bitmap.createBitmap(
            sizeOfOneItem * actionList.size * 2,
            sizeOfOneItem * 2,
            Bitmap.Config.ARGB_8888
        )

        val canvasDrawable = Canvas(fullBitmap)
        for (i in 0 until actionList.size) {
            val itemDrawable = iconController.getItemIconDrawable(actionList[i])
            val itemBitmap = (itemDrawable as BitmapDrawable).bitmap
            val scaledItemBitmap = Bitmap.createScaledBitmap(itemBitmap, sizeOfOneItem * 2, sizeOfOneItem * 2, false)
            canvasDrawable.drawBitmap(scaledItemBitmap, (i * sizeOfOneItem * 2).toFloat(), 0f, paint)
        }

        indentLength =
            convertToPx(fullBitmap.width, view.resources) -
                    (COUNT_OF_VISIBLE_ITEMS * 2 * convertToPx(sizeOfOneItem, view.resources))
        val indentInaccuracy = (Math.random() * convertToPx(sizeOfOneItem, view.resources) * 2).toInt()
        indentLength -= indentInaccuracy

        return fullBitmap
    }

    private fun refreshTimer() {
        timer.cancel()
        timer = Timer()
        timerTask = MoveViewTimerTask()
    }

    private inner class MoveViewTimerTask : TimerTask() {

        private var acceleration = 0.85f
        private var isSpeeding = true

        override fun run() {
            indent += (frameStandardMovingSize * view.scrollSpeed * acceleration).toInt()
            val indentPx = convertToPx(indent, view.resources)

            view.getActivity()?.runOnUiThread {
                updateUi(indentPx)
            }

            if (isSpeeding) {
                accelerate(indentPx)
            } else {
                slowdown(indentPx)
            }
            if (indentPx >= indentLength) {
                refreshTimer()
            }
        }

        private fun updateUi(indentPx: Int) {
            view.invalidate()
            logInf(
                MAIN_LOGGER_TAG,
                "indent = $indent, indentPx = $indentPx frameStandardMovingSize = $frameStandardMovingSize, " +
                        "indentLength = $indentLength, acceleration = $acceleration"
            )
        }

        private fun accelerate(indentPx: Int) {
            if (indentPx <= indentLength / 2) {
                acceleration += 0.0025f
            }
            if (indentPx >= indentLength / 2.5) {
                isSpeeding = false
            }
        }

        private fun slowdown(indentPx: Int) {
            if (indentPx >= indentLength * 0.9) {
                if (acceleration >= 0.2)
                    acceleration -= 0.005f
                return
            }
            if (acceleration >= 0.5) {
                acceleration -= 0.0025f
            }
        }
    }
}