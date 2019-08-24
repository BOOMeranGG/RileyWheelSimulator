package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.baseView.BaseView
import com.orange_infinity.rileywheelsimulator.util.ANIMATION_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val DP_DEFAULT_WIDTH = 72
private const val DP_DEFAULT_HEIGHT = 350

class RileyWheelView(context: Context, attrs: AttributeSet) : BaseView(context, attrs) {

    private val drawer = WheelDrawer(this)
    private val touchHandler = WheelTouchHandler(this)
    var positionY = 0

    override fun onDraw(canvas: Canvas) {
        drawer.draw(canvas, context)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val resolvedWidthSpec = resolveMeasureSpec(widthMeasureSpec, DP_DEFAULT_WIDTH)
        val resolvedHeightSpec = resolveMeasureSpec(heightMeasureSpec, DP_DEFAULT_HEIGHT)

        super.onMeasure(resolvedWidthSpec, resolvedHeightSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        positionY = 1
        logInf(ANIMATION_LOGGER_TAG, "positionY = $positionY")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return touchHandler.onTouchEvent(event)
    }

    fun moveSlider(newY: Int) {
        positionY = newY
        invalidate()
    }

    fun setListener(listener: Listener) {
        touchHandler.listener = listener
    }

    open class Listener {

        open fun onScrollSateChanged(positionY: Int) {
        }

        open fun onScrollEnded(positionY: Int) {
        }
    }
}