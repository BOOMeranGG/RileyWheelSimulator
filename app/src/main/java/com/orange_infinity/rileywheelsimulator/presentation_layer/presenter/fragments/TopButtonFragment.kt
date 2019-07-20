package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.MainActivity

class TopButtonFragment : Fragment() {

    private lateinit var btnRileyWheel: Button
    private lateinit var btnTreasure: Button
    private lateinit var btnInventory: Button
    private lateinit var btnCasino: Button

    companion object {
        fun newInstance(): TopButtonFragment {
            return TopButtonFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_top_button, container, false)
        btnRileyWheel = v.findViewById(R.id.btnRileyWheel)
        btnTreasure = v.findViewById(R.id.btnTreasure)
        btnInventory = v.findViewById(R.id.btnInventory)
        btnCasino = v.findViewById(R.id.btnCasino)

        btnRileyWheel.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).changeFragment(RileyWheelFragment.newInstance())
        })

        btnTreasure.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).changeFragment(TreasureFragment.newInstance())
        })

        btnInventory.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).changeFragment(InventoryFragment.newInstance())
        })

        btnCasino.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).changeFragment(CasinoFragment.newInstance())
        })

        return v
    }
}
