package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class ItemPickerFragment : DialogFragment(), View.OnClickListener {

    private lateinit var imgItem: ImageView
    private lateinit var tvCost: TextView
    private lateinit var tvItemName: TextView
    private lateinit var btnSell: Button
    private lateinit var btnOk: Button

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
        logInf(MAIN_LOGGER_TAG, "ItemPickerFragment for ${item.getItemName()} was created")

        imgItem = v.findViewById(R.id.imgItem)
        tvCost = v.findViewById(R.id.tvCost)
        tvItemName = v.findViewById(R.id.tvItemName)
        btnSell = v.findViewById(R.id.btnSell)
        btnOk = v.findViewById(R.id.btnOk)

        btnSell.setOnClickListener(this)
        btnOk.setOnClickListener(this)

        imgItem.setImageResource(R.drawable.dota2_logo)
        tvCost.text = "${item.getCost()}$"
        tvItemName.text = item.getItemName()
        //TODO("В Title задавать название героя, если оно есть, иначе ---> что за предмет")
        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle("Item info")
            .setCancelable(true)
            .create()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSell -> {
                logInf(MAIN_LOGGER_TAG, "\"Sell\" button was clicked")
                //TODO("Удаление предмета. Деньги с продажи должны переходить в опыт компендиума")
            }
            R.id.btnOk -> {
                logInf(MAIN_LOGGER_TAG, "\"OK\" button was clicked")
                dismiss()
            }
        }
    }
}