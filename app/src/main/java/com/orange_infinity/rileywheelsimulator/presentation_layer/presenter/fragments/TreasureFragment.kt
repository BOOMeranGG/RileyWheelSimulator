package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox

class TreasureFragment : InventoryTreasureFragment() {

    companion object {
        fun newInstance(): TreasureFragment = TreasureFragment()
    }

    override fun setAdapter() {
        inventoryRecyclerView.adapter = TreasureAdapter(items)
    }

    override fun getNeededItems() {
        items = inventoryController.getTreasuresFromInventory()
    }

    private inner class TreasureHolder(inflater: LayoutInflater, container: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_inventory, container, false)) {

        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)

        @SuppressLint("SetTextI18n")
        fun bindTreasure(itemBox: ItemBox) {
            tvItemName.text = "${itemBox.item.getItemName()}\n${itemBox.item.getCost()}\$"
            tvCount.text = "X${itemBox.count}"
            imgItem.setImageResource(R.drawable.treasure_logo)
        }
    }

    private inner class TreasureAdapter(private val items: List<ItemBox>) : RecyclerView.Adapter<TreasureHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreasureHolder {
            val inflater = LayoutInflater.from(activity)
            return TreasureHolder(inflater, parent)
        }

        override fun onBindViewHolder(treasureHolder: TreasureHolder, position: Int) {
            val treasure = items[position]
            treasureHolder.bindTreasure(treasure)
        }

        override fun getItemCount(): Int = items.size
    }
}