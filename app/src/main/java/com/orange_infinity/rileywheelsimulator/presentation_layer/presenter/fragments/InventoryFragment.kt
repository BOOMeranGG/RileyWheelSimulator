package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.ItemPickerFragment
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val ITEM_PICKER = "itemPicker"

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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_inventory, container, false)),
        View.OnClickListener {

        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        private val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
        private lateinit var item: Item
        var itemCount: Int = 1

        @SuppressLint("SetTextI18n")
        fun bindItem(itemBox: ItemBox) {
            item = itemBox.item
            itemCount = itemBox.count
            tvItemName.text = "${itemBox.item.getItemName()}\n${itemBox.item.getCost()}\$"
            tvCount.text = "X${itemBox.count}"
            itemLayout.setOnClickListener(this)

            val icon = iconController.getDrawable(item)
            if (icon != null) {
                imgItem.setImageDrawable(icon)
            } else {
                imgItem.setImageResource(R.drawable.dota2_logo)
            }
        }

        override fun onClick(v: View?) {
            logInf(MAIN_LOGGER_TAG, "Item \"${item.getItemName()}\" was clicked")
            val dialog = ItemPickerFragment.newInstance(item, itemCount)
            dialog.show(fragmentManager, ITEM_PICKER)
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
