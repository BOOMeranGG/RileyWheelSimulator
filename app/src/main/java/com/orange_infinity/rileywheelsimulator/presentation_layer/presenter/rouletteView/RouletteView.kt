package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.rouletteView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.baseView.BaseView
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val DP_DEFAULT_WIDTH = 500
private const val DP_DEFAULT_HEIGHT = 145

class RouletteView(context: Context, attrs: AttributeSet) : BaseView(context, attrs) {

    private var iconController = IconController.getInstance(context)
    private var drawer = RouletteDrawer(this, iconController)
    private lateinit var itemList: List<Item>
    var isStarted = false
    var scrollSpeed = 0f

    override fun onDraw(canvas: Canvas) {
        if (scrollSpeed == 0f) {
            drawer.drawStatic(canvas, context)
        } else {
            drawer.startMove(canvas, itemList)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val resolvedWidthSpec = resolveMeasureSpec(widthMeasureSpec, DP_DEFAULT_WIDTH)
        val resolvedHeightSpec = resolveMeasureSpec(heightMeasureSpec, DP_DEFAULT_HEIGHT)

        super.onMeasure(resolvedWidthSpec, resolvedHeightSpec)
    }

    fun clearView() {
        scrollSpeed = 0f
        isStarted = false
        stopMove()
        invalidate()
    }

    fun startMoveWithWinnerThirdFromEnd(itemList: List<Item>, scrollSpeed: Float) {
        this.itemList = itemList
        isStarted = true
        this.scrollSpeed = scrollSpeed
        invalidate()
        logInf(MAIN_LOGGER_TAG, "Start moving RouletteView")
    }

    fun stopMove() {
        drawer.stopMove()
        drawer = RouletteDrawer(this, iconController)
    }
}