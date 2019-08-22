package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view.RileyWheelView
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.rouletteView.RouletteView
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_RILEY_PLAY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.RileyItemController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import android.view.LayoutInflater as LayoutInflater1

class RileyWheelFragment : Fragment() {

    private lateinit var rileyWheelViewController: RileyWheelView
    private lateinit var rouletteViewController: RouletteView
    private lateinit var rileyItemController: RileyItemController
    private lateinit var soundPlayer: SoundPlayer
    private val itemList = listOf<Item>(
        Arcana.BladesOfVothDomosh,
        Courier.BlottoAndStick,
        Arcana.TempestHelmOfTheThundergod,
        Treasure.ImmortalTreasureI2016,
        SetItem.BindingsOfFrost,
        Arcana.FrostAvalanche,
        Courier.Itsy,
        Courier.Mok,
        SetItem.Bladesrunner,
        SetItem.ChainedMistress,
        Arcana.BladesOfVothDomosh,
        Courier.BlottoAndStick,
        Arcana.TempestHelmOfTheThundergod,
        Treasure.ImmortalTreasureI2016, // WINNER!
        Arcana.FrostAvalanche,
        Courier.Itsy                    // NUMBER 16
    )

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

        var newItem: Item?
        rileyWheelViewController.setListener(object : RileyWheelView.Listener() {
            override fun onScrollEnded(positionY: Int) {
                newItem = rileyItemController.addRandomItem()
                logInf(MAIN_LOGGER_TAG, "Add new item: " + newItem.toString())
                soundPlayer.standardPlay(SOUND_RILEY_PLAY)

                if (rouletteViewController.isStarted) {
                    rouletteViewController.clearView()
                } else {
                    rouletteViewController.startMoveWithWinnerThirdFromEnd(itemList, 1f)
                }
            }
        })

        return v
    }

    override fun onPause() {
        super.onPause()
        rouletteViewController.stopMove()
    }
}
