package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.ItemPickerFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.TreasurePickerFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view.RileyWheelView
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.rouletteView.RouletteView
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_RILEY_PLAY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.RileyItemController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import android.view.LayoutInflater as LayoutInflater1

private const val COUNT_OF_ITEMS = 16
private const val WINNER_POSITION = 13
private const val TREASURE_PICKER = "treasurePicker"
private const val ITEM_PICKER = "itemPicker"

class RileyWheelFragment : Fragment(), RouletteView.RouletteHandler {

    private lateinit var rileyWheelViewController: RileyWheelView
    private lateinit var rouletteViewController: RouletteView
    private lateinit var rileyItemController: RileyItemController
    private lateinit var soundPlayer: SoundPlayer
    private lateinit var winnerItem: Item

    companion object {
        fun newInstance(): RileyWheelFragment = RileyWheelFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rileyItemController = RileyItemController(InventoryRepositoryImpl.getInstance(context?.applicationContext))
        soundPlayer = SoundPlayer.getInstance(activity?.applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater1, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_riley_wheel, container, false)
        rileyWheelViewController = v.findViewById(R.id.wheelViewController)
        rouletteViewController = v.findViewById(R.id.rouletteViewController)

        rileyWheelViewController.setListener(object : RileyWheelView.Listener() {
            override fun onScrollEnded(positionY: Int) {
                val itemList = createRandomItemList()
                winnerItem = itemList[WINNER_POSITION]
                soundPlayer.standardPlay(SOUND_RILEY_PLAY)
                rileyItemController.saveRileyItem(winnerItem)
                logInf(MAIN_LOGGER_TAG, "Add new item: $winnerItem")

                if (rouletteViewController.isStarted) {
                    rouletteViewController.clearView()
                } else {
                    rouletteViewController.startMoveWithWinnerThirdFromEnd(this@RileyWheelFragment, itemList, 1f)
                }
            }
        })

        return v
    }

    override fun onPause() {
        super.onPause()
        rouletteViewController.stopMove()
        soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
    }

    override fun onRouletteMoveEnd() {
        if (winnerItem is Treasure) {
            val dialog = TreasurePickerFragment.newInstance(winnerItem as Treasure, 1)
            dialog.show(fragmentManager, TREASURE_PICKER)
        } else {
            val dialog = ItemPickerFragment.newInstance(winnerItem, 1)
            dialog.show(fragmentManager, ITEM_PICKER)
        }
    }

    private fun createRandomItemList(): List<Item> {
        val itemList = mutableListOf<Item>()
        for (i in 0 until COUNT_OF_ITEMS) {
            itemList.add(rileyItemController.getRandomItem())
        }
        return itemList
    }
}
