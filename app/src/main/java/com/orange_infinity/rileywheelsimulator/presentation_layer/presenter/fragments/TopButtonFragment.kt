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
    private lateinit var btnAccount: Button

    companion object {
        private lateinit var hostActivity: MainActivity

        fun newInstance(activity: MainActivity): TopButtonFragment {
            hostActivity = activity
            return TopButtonFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.top_button_fragment, container, false)
        btnRileyWheel = v.findViewById(R.id.btnRileyWheel)
        btnTreasure = v.findViewById(R.id.btnTreasure)
        btnInventory = v.findViewById(R.id.btnInventory)
        btnAccount = v.findViewById(R.id.btnAccount)

        btnRileyWheel.setOnClickListener(View.OnClickListener {
            hostActivity.changeFragment(RileyWheelFragment.newInstance())
        })

        btnTreasure.setOnClickListener(View.OnClickListener {
            hostActivity.changeFragment(TreasureFragment.newInstance())
        })

        btnInventory.setOnClickListener(View.OnClickListener {
            hostActivity.changeFragment(InventoryFragment.newInstance())
        })

        btnAccount.setOnClickListener(View.OnClickListener {
            hostActivity.changeFragment(AccountFragment.newInstance())
        })

        return v
    }
}
