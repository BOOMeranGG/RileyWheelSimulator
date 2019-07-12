package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item

class ItemPickerFragment : DialogFragment() {

    private lateinit var imgItem: ImageView
    private lateinit var tvCount: TextView
    private lateinit var tvItemName: TextView

    lateinit var item: Item
    var itemCount: Int = 1

    companion object {
        fun newInstance(item: Item, count: Int): ItemPickerFragment =
            ItemPickerFragment().also {
                it.item = item
                it.itemCount = count
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_item, null)

        imgItem = v.findViewById(R.id.imgItem)
        tvCount = v.findViewById(R.id.tvCount)
        tvItemName = v.findViewById(R.id.tvItemName)

        imgItem.setImageResource(R.drawable.dota2_logo)

        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle("Item info")
            .setPositiveButton(android.R.string.ok, null)
            .setCancelable(true)
            .create()
    }
}