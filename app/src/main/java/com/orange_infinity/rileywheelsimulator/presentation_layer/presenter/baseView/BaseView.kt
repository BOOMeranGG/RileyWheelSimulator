package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.baseView

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.View
import com.orange_infinity.rileywheelsimulator.util.convertToPx

open class BaseView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    protected fun resolveMeasureSpec(measureSpec: Int, dpDefault: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        if (mode == MeasureSpec.EXACTLY) {
            return measureSpec
        }
        var defaultSize = convertToPx(dpDefault, resources)
        if (mode == MeasureSpec.AT_MOST) {
            defaultSize = Math.min(defaultSize, MeasureSpec.getSize(measureSpec))
        }
        return MeasureSpec.makeMeasureSpec(defaultSize, MeasureSpec.EXACTLY)
    }

    fun getActivity(): Activity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }
}