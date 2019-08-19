package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.baseView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import com.orange_infinity.rileywheelsimulator.util.convertToPx

open class BaseDrawer(private val view: View) {

    protected fun getScaledSideBitmap(
        context: Context,
        destWidth: Int,
        destHeight: Int,
        drawableId: Int
    ): Bitmap {
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