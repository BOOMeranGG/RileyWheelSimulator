package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox

class InventoryFragment : InventoryTreasureFragment() {

    companion object {
        fun newInstance(): InventoryFragment = InventoryFragment()
    }

    override fun setAdapter() {
        inventoryRecyclerView.adapter = ItemAdapter(items)
    }

    override fun getNeededItems() {
        items = inventoryController.getItemsFromInventory()
    }

    private inner class ItemHolder(inflater: LayoutInflater, container: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_inventory, container, false)) {

        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)

        @SuppressLint("SetTextI18n")
        fun bindItem(itemBox: ItemBox) {
            tvItemName.text = "${itemBox.item.getItemName()}\n${itemBox.item.getCost()}\$"
            tvCount.text = "X${itemBox.count}"
            imgItem.setImageResource(R.drawable.dota2_logo)
        }
    }

    private inner class ItemAdapter(private val items: List<ItemBox>) : RecyclerView.Adapter<ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val inflater = LayoutInflater.from(activity)
            return ItemHolder(inflater, parent)
        }

        override fun onBindViewHolder(itemHolder: ItemHolder, position: Int) {
            val item = items[position]
            itemHolder.bindItem(item)
        }

        override fun getItemCount(): Int = items.size
    }
}
