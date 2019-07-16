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
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Treasure
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.TreasurePickerFragment
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val TREASURE_PICKER = "treasurePicker"

class TreasureFragment : InventoryTreasureFragment() {

    private var adapter: TreasureAdapter? = null

    companion object {
        fun newInstance(): TreasureFragment = TreasureFragment()
    }

    override fun setAdapter() {
        inventoryRecyclerView.adapter = TreasureAdapter(items)
    }

    override fun getNeededItems() {
        items = inventoryController.getTreasuresFromInventory()
    }

    override fun updateRecycler() {
        if (adapter == null) {
            adapter = TreasureAdapter(items)
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    private inner class TreasureHolder(inflater: LayoutInflater, container: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_inventory, container, false)),
        View.OnClickListener {

        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        private val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
        private lateinit var treasure: Treasure
        var treasureCount: Int = 1

        @SuppressLint("SetTextI18n")
        fun bindTreasure(itemBox: ItemBox) {
            treasure = itemBox.item as Treasure
            treasureCount = itemBox.count
            tvItemName.text = "${itemBox.item.getItemName()}\n${itemBox.item.getCost()}\$"
            tvCount.text = "X${itemBox.count}"
            itemLayout.setOnClickListener(this)

            imgItem.setImageResource(R.drawable.treasure_logo)
        }

        override fun onClick(v: View?) {
            logInf(MAIN_LOGGER_TAG, "Item \"${treasure.getItemName()}\" was clicked")
            val dialog = TreasurePickerFragment.newInstance(treasure, treasureCount)
            dialog.show(fragmentManager, TREASURE_PICKER)
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