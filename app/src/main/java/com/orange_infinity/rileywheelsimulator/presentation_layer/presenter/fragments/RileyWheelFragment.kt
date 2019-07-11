package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.uses_case_layer.RileyController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import android.view.LayoutInflater as LayoutInflater1

class RileyWheelFragment : Fragment() {

    private lateinit var btnAddItem: Button
    private lateinit var rileyController: RileyController

    companion object {
        fun newInstance(): RileyWheelFragment = RileyWheelFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rileyController = RileyController(InventoryRepositoryImpl.getInstance(context?.applicationContext))
    }

    override fun onCreateView(inflater: LayoutInflater1, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.riley_wheel_fragment, container, false)
        btnAddItem = v.findViewById(R.id.btnAddItem)

        var newItem: Item?
        btnAddItem.setOnClickListener(View.OnClickListener {
            newItem = rileyController.addRandomItem()
            logInf(MAIN_LOGGER_TAG, "Add new item: " + newItem.toString())
        })
        return v
    }

}