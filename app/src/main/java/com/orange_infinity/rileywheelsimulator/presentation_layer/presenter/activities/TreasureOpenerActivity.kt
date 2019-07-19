package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.*
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.ItemsInTreasureRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.uses_case_layer.IconController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.TreasureOpener
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

const val TREASURE_OPENER = "treasureOpener"
const val TREASURE_COUNT = "treasureCount"

class TreasureOpenerActivity : AppCompatActivity(), ViewSwitcher.ViewFactory {

    private lateinit var treasureOpener: TreasureOpener
    private lateinit var iconController: IconController
    private lateinit var treasureName: String
    private lateinit var imgFirstItem: ImageSwitcher
    private lateinit var imgSecondItem: ImageSwitcher
    private var itemCount: Int = 1
    private var itemList = arrayOf<InnerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treasure_opener)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        treasureOpener = TreasureOpener(ItemsInTreasureRepositoryImpl.getInstance(applicationContext))
        iconController = IconController.getInstance(applicationContext)
        treasureName = intent.getSerializableExtra(TREASURE_OPENER) as String
        itemCount = intent.getSerializableExtra(TREASURE_COUNT) as Int
        itemList = treasureOpener.getItemSet(treasureName)
        logInf(MAIN_LOGGER_TAG, "Open treasure: $treasureName(count = $itemCount), find ${itemList.size} items")

        setSwitcher()
    }

    override fun makeView(): View {
        val img = ImageView(this)
        img.layoutParams =
            FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        //img.scaleType = ImageView.ScaleType.FIT_XY
        return img
    }

    private fun setSwitcher() {
        imgFirstItem = findViewById(R.id.imgFirstItem)
        imgFirstItem.setFactory(this)
        imgSecondItem = findViewById(R.id.imgSecondItem)
        imgSecondItem.setFactory(this)

        imgFirstItem.setImageDrawable(iconController.getItemIconDrawable(itemList.first()))
        imgSecondItem.setImageDrawable(iconController.getItemIconDrawable(itemList[1]))

        val inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration = 1000
        val outAnimation = AlphaAnimation(1f, 0f)
        outAnimation.duration = 1000

        imgFirstItem.inAnimation = inAnimation
        imgFirstItem.outAnimation = outAnimation
        imgSecondItem.inAnimation = inAnimation
        imgSecondItem.outAnimation = outAnimation
    }
}
