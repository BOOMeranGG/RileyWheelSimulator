package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.riley_wheel_view.RileyWheelView
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_RILEY_PLAY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.RileyItemController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import android.view.LayoutInflater as LayoutInflater1

class RileyWheelFragment : Fragment() {

    private lateinit var rileyWheelViewController: RileyWheelView
    private lateinit var rileyItemController: RileyItemController
    private lateinit var soundPlayer: SoundPlayer

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

        var newItem: Item?
        rileyWheelViewController.setListener(object : RileyWheelView.Listener() {
            override fun onScrollEnded(positionY: Int) {
                newItem = rileyItemController.addRandomItem()
                logInf(MAIN_LOGGER_TAG, "Add new item: " + newItem.toString())
                soundPlayer.standardPlay(SOUND_RILEY_PLAY)
            }
        })

        return v
    }
}
