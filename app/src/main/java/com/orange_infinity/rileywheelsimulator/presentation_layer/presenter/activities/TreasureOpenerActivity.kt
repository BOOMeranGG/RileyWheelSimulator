package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.*
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InnerItemsRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Treasure
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.ItemPickerFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

private const val WAITING = 1500L
private const val MAX_TREASURE_OPENING = 5
private const val ITEM_PICKER = "itemPicker"

const val TREASURE_OPENER = "treasureOpener"
const val TREASURE_COUNT = "treasureCount"
const val TREASURE_OBJECT = "treasureObject"

class TreasureOpenerActivity : AppCompatActivity(), ViewSwitcher.ViewFactory, View.OnClickListener {

    private lateinit var openerController: TreasureOpenerController
    private lateinit var iconController: IconController
    private lateinit var inventoryController: InventoryController
    private lateinit var treasureName: String
    private lateinit var imgFirstItem: ImageSwitcher
    private lateinit var imgSecondItem: ImageSwitcher
    private lateinit var linearInnerItems: LinearLayout
    private lateinit var linearMainItems: LinearLayout
    private lateinit var soundPlayer: SoundPlayer
    private lateinit var firstItem: InnerItem
    private lateinit var secondItem: InnerItem
    private lateinit var thisTreasure: Treasure
    private lateinit var btnCancel: Button
    private lateinit var btnOpen: Button

    private var timer = Timer()
    private var timerTask = OpeningTimerTask()
    private var itemCount: Int = 1
    private var itemList = mutableListOf<InnerItem>()
    private var deletedItems = mutableListOf<InnerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treasure_opener)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        linearInnerItems = findViewById(R.id.linearInnerItems)
        linearInnerItems.setOnClickListener {
            val intent = Intent(this, InnerItemViewPager::class.java)
            intent.putExtra(TREASURE_NAME_INTENT_KEY, treasureName)
            startActivity(intent)
        }

        linearMainItems = findViewById(R.id.linearMainItems)

        btnCancel = findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener(this)
        btnCancel.isEnabled = false

        btnOpen = findViewById(R.id.btnOpen)
        btnOpen.setOnClickListener(this)

        openerController = TreasureOpenerController(InnerItemsRepositoryImpl.getInstance(applicationContext))
        iconController = IconController.getInstance(applicationContext)
        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(applicationContext))
        treasureName = intent.getSerializableExtra(TREASURE_OPENER) as String
        itemCount = intent.getSerializableExtra(TREASURE_COUNT) as Int
        thisTreasure = intent.getSerializableExtra(TREASURE_OBJECT) as Treasure

        itemList = openerController.createItemSet(treasureName).toMutableList()
        logInf(MAIN_LOGGER_TAG, "Open treasure: $treasureName(count = $itemCount), find ${itemList.size} items")

        soundPlayer = SoundPlayer.getInstance(applicationContext)
        setSwitcher()
        createTopInnerItems()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnCancel) {
            if (btnOpen.isEnabled) {
                return
            }
            timer.cancel()
            val winnerItem = TreasureOpenerController.getFastWinner(itemList, deletedItems)
            controlWinnerItem(winnerItem)
            prepareNextOpening()
            logInf(MAIN_LOGGER_TAG, "Cancel opener animation")
        } else if (v.id == R.id.btnOpen) {
            btnOpen.isEnabled = false
            btnCancel.isEnabled = true
            startOpening()
        }
    }

    override fun makeView(): View {
        val img = ImageView(this)
        val layoutParams = FrameLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        img.layoutParams = layoutParams
        img.setBackgroundResource(R.color.colorBlack)
        img.setPadding(8, 8, 8, 8)
        img.scaleType = ImageView.ScaleType.FIT_XY
        return img
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun startOpening() {
        logInf(MAIN_LOGGER_TAG, "Start opening treasure")
        if (!isTreasureInInventory(thisTreasure)) {
            Toast.makeText(this, "Don't have enough Treasure", Toast.LENGTH_LONG).show()
            btnCancel.isEnabled = false
            return
        }
        inventoryController.deleteItem(thisTreasure)
        timer.schedule(timerTask, 0, WAITING * 2)
    }

    private fun isTreasureInInventory(treasure: Treasure): Boolean {
        val treasures = inventoryController.getTreasuresFromInventory()
        return treasures.find { it.item.getName() == treasure.getName() } != null
    }

    private fun createTopInnerItems() {
        linearInnerItems.removeAllViews()
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams.weight = 1f

        for (item in itemList) {
            val img = ImageView(this)
            if (deletedItems.find { it.getName() == item.getName() } != null) {
                img.setImageDrawable(null)
            } else {
                img.setImageDrawable(iconController.getItemIconDrawable(item))
            }
            img.layoutParams = layoutParams
            linearInnerItems.addView(img)
        }
    }

    private fun setSwitcher() {
        imgFirstItem = findViewById(R.id.imgFirstItem)
        imgFirstItem.setFactory(this)
        imgSecondItem = findViewById(R.id.imgSecondItem)
        imgSecondItem.setFactory(this)

        val inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration = WAITING
        val outAnimation = AlphaAnimation(1f, 0f)
        outAnimation.duration = WAITING

        imgFirstItem.inAnimation = inAnimation
        imgFirstItem.outAnimation = outAnimation
        imgSecondItem.inAnimation = inAnimation
        imgSecondItem.outAnimation = outAnimation

        createFrontImg()
    }

    private fun createFrontImg() {
        firstItem = openerController.gerRandomItem(null)
        secondItem = openerController.gerRandomItem(firstItem)

        imgFirstItem.setImageDrawable(iconController.getItemIconDrawable(firstItem))
        imgSecondItem.setImageDrawable(iconController.getItemIconDrawable(secondItem))
    }

    private fun removeLooserItem() {
        val looser = openerController.getLooserBetweenDoubleItems(firstItem, secondItem)
        logInf(MAIN_LOGGER_TAG, "Item ${looser.getName()} is lose")

        when (looser) {
            firstItem -> {
                val newItem = openerController.gerRandomItem(secondItem)
                imgFirstItem.setImageDrawable(iconController.getItemIconDrawable(newItem))
                firstItem = newItem
            }
            secondItem -> {
                val newItem = openerController.gerRandomItem(firstItem)
                imgSecondItem.setImageDrawable(iconController.getItemIconDrawable(newItem))
                secondItem = newItem
            }
        }
    }

    private fun controlWinnerItem(winnerItem: InnerItem) {
        logInf(MAIN_LOGGER_TAG, "Winner is ${openerController.getWinnerItem().getItemName()}")
        btnCancel.isEnabled = false
        btnOpen.isEnabled = true
        deletedItems.add(winnerItem)

        inventoryController.addItem(winnerItem)
        soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
        createTopInnerItems()
        val dialog = ItemPickerFragment.newInstance(winnerItem, 1)
        dialog.show(supportFragmentManager, ITEM_PICKER)
    }

    private fun prepareNextOpening() {
        timer = Timer()
        btnOpen.isEnabled = true
        timerTask = OpeningTimerTask()
        val remainingItems = openerController.createItemSetWithoutExceptList(treasureName, deletedItems)

        if (deletedItems.size == MAX_TREASURE_OPENING || remainingItems.size == 2) {
            btnOpen.isEnabled = false
            logInf(MAIN_LOGGER_TAG, "Max treasures was opened")
        }
    }

    private inner class OpeningTimerTask : TimerTask() {

        override fun run() {
            if (openerController.isWin()) {
                this.cancel()
            }
            runOnUiThread {
                if (openerController.isWin()) {
                    controlWinnerItem(openerController.getWinnerItem())
                    prepareNextOpening()
                    createFrontImg()
                    return@runOnUiThread
                }
                removeLooserItem()
            }
        }
    }
}
