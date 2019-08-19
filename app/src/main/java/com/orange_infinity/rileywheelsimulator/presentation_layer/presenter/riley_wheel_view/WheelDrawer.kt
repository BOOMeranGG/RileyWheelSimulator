package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view

import android.content.Context
import android.graphics.*
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.baseView.BaseDrawer

private const val DRAWABLE_SLIDER_ID = R.drawable.slider
private const val DRAWABLE_SLIDER_BACK_ID = R.drawable.slider_back
private const val RATION_WIDTH_HEIGHT = 0.85f

class WheelDrawer(val view: RileyWheelView) : BaseDrawer(view) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var x = 0
    private var y = 0

    fun draw(canvas: Canvas, context: Context) {
        x = view.width / 2
        y = view.height / 2

        drawBack(canvas, context)
        drawSlider(canvas, context)
    }

    private fun drawSlider(canvas: Canvas, context: Context) {
        val min = Math.min(x, y)
        val width = min * 10 / 12
        val height = (width * RATION_WIDTH_HEIGHT).toInt()
        val bitmapSlider = getScaledSideBitmap(context, width, height, DRAWABLE_SLIDER_ID)
        canvas.drawBitmap(bitmapSlider, ((view.width - width) / 7).toFloat(), view.positionY.toFloat(), paint)

        Slider.width = width
        Slider.height = height
    }

    private fun drawBack(canvas: Canvas, context: Context) {
        val bitmapBackground = getScaledSideBitmap(context, x, y, DRAWABLE_SLIDER_BACK_ID)
        canvas.drawBitmap(bitmapBackground, 0f, 0f, paint)
    }
}