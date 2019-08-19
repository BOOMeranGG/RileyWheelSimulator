package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view

import android.view.GestureDetector
import android.view.MotionEvent
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class WheelTouchHandler(val view: RileyWheelView) : GestureDetector.SimpleOnGestureListener() {

    private val gestureDetector = GestureDetector(view.context, this)
    var listener: RileyWheelView.Listener? = null

    fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            if (view.positionY > view.height / 4) {
                listener?.onScrollEnded(view.positionY)
            }
            returnView()
            logInf(MAIN_LOGGER_TAG, "Down event triggered(RileyWheelView), positionY = ${view.positionY}")
        }
        return true
    }

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        val maxY = view.height - (Slider.height * 2)
        val minY = 1
        if (e2.y <= maxY && e2.y >= minY) {
            view.moveSlider(e2.y.toInt())
            listener?.onScrollSateChanged(view.positionY)
            logInf(MAIN_LOGGER_TAG, "On Scroll event triggered(RileyWheelView), y = ${e2.y}, minY = $minY, maxY = $maxY")
        }
        return true
    }

    private fun returnView() {
        view.moveSlider(1)
    }
}