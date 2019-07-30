package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view

import android.content.Context
import android.graphics.*
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.util.convertToPx

private const val DRAWABLE_SLIDER_ID = R.drawable.slider
private const val DRAWABLE_SLIDER_BACK_ID = R.drawable.slider_back
private const val RATION_WIDTH_HEIGHT = 0.85f

class Drawer(val view: RileyWheelView) {

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
        val bitmapSlider = getScaledBitmap(context, width, height, DRAWABLE_SLIDER_ID)
        canvas.drawBitmap(bitmapSlider, ((view.width - width) / 7).toFloat(), view.positionY.toFloat(), paint)

        Slider.width = width
        Slider.height = height
    }

    private fun drawBack(canvas: Canvas, context: Context) {
        val bitmapBackground = getScaledBitmap(context, x, y, DRAWABLE_SLIDER_BACK_ID)
        canvas.drawBitmap(bitmapBackground, 0f, 0f, paint)
    }

    private fun getScaledBitmap(context: Context, destWidth: Int, destHeight: Int, drawableId: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, drawableId, options)

        val srcWidth = options.outWidth
        val srcHeight = options.outHeight

        var inSampleSize = 1
        if (srcHeight > destHeight || srcWidth > destWidth) {
            inSampleSize = if (srcWidth > srcHeight) {
                Math.round(srcHeight.toFloat() / destHeight.toFloat())
            } else {
                Math.round(srcWidth.toFloat() / destWidth.toFloat())
            }
        }

        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize
        val fullBitmap = BitmapFactory.decodeResource(context.resources, drawableId, options)

        return Bitmap.createScaledBitmap(
            fullBitmap,
            convertToPx(destWidth, view.resources),
            convertToPx(destHeight, view.resources), true
        )
    }
}