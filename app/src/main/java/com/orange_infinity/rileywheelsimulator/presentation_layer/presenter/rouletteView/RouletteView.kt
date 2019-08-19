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
    var scrollSpeed = 0

    override fun onDraw(canvas: Canvas) {
        if (scrollSpeed == 0) {
            drawer.drawStatic(canvas, context)
        } else {
            drawer.startMove(itemList, canvas)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val resolvedWidthSpec = resolveMeasureSpec(widthMeasureSpec, DP_DEFAULT_WIDTH)
        val resolvedHeightSpec = resolveMeasureSpec(heightMeasureSpec, DP_DEFAULT_HEIGHT)

        super.onMeasure(resolvedWidthSpec, resolvedHeightSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    fun startMoveWithWinnerThirdFromEnd(itemList: List<Item>) {
        this.itemList = itemList
        scrollSpeed = 1
        invalidate()
        logInf(MAIN_LOGGER_TAG, "Start moving RouletteView")
    }

    fun stopMove() {
        drawer.stopMove()
        drawer = RouletteDrawer(this, iconController)
    }
}