package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox
import com.orange_infinity.rileywheelsimulator.uses_case_layer.InventoryController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.IconController

abstract class InventoryTreasureFragment : Fragment() {

    protected lateinit var inventoryController: InventoryController
    protected lateinit var iconController: IconController
    protected lateinit var inventoryRecyclerView: RecyclerView
    protected lateinit var items: List<ItemBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(context?.applicationContext))
        iconController = IconController.getInstance(context?.applicationContext)
        getNeededItems()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.inventory_fragment, container, false)

        inventoryRecyclerView = v.findViewById(R.id.fieldRecyclerView)
        inventoryRecyclerView.layoutManager = GridLayoutManager(activity, 3)
        setAdapter()
        return v
    }

    abstract fun setAdapter()

    abstract fun getNeededItems()

    abstract fun updateRecycler()
}