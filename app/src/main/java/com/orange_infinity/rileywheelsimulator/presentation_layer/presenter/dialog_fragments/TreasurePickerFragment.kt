package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Treasure
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.TREASURE_COUNT
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.TREASURE_OBJECT
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.TREASURE_OPENER
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.TreasureOpenerActivity
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class TreasurePickerFragment : DialogFragment(), View.OnClickListener {

    private lateinit var imgItem: ImageView
    private lateinit var tvCost: TextView
    private lateinit var tvItemName: TextView
    private lateinit var tvRarity: TextView
    private lateinit var btnSell: Button
    private lateinit var btnOk: Button

    lateinit var treasure: Treasure
    var itemCount: Int = 1

    companion object {
        fun newInstance(treasure: Treasure, count: Int): TreasurePickerFragment =
            TreasurePickerFragment().also {
                it.treasure = treasure
                it.itemCount = count
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_item, null)
        logInf(MAIN_LOGGER_TAG, "TreasurePickerFragment for ${treasure.getItemName()} was created")

        imgItem = v.findViewById(R.id.imgItem)
        tvCost = v.findViewById(R.id.tvCost)
        tvItemName = v.findViewById(R.id.tvItemName)
        tvRarity = v.findViewById(R.id.tvRarity)
        btnSell = v.findViewById(R.id.btnSell)
        btnOk = v.findViewById(R.id.btnOk)

        btnSell.setOnClickListener(this)
        btnSell.text = "Open"
        btnOk.setOnClickListener(this)

        imgItem.setImageDrawable(
            IconController.getInstance(context?.applicationContext)
                .getItemIconDrawableWithBox(treasure)
        )
        tvCost.text = "${treasure.getCost()}$"
        tvItemName.text = treasure.getItemName()
        tvRarity.text = treasure.getRarity()

        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle("Item info")
            .setCancelable(true)
            .create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSell -> {
                logInf(MAIN_LOGGER_TAG, "\"Open\" button was clicked")
                val intent = Intent(activity, TreasureOpenerActivity::class.java)
                intent.putExtra(TREASURE_OPENER, treasure.name)
                intent.putExtra(TREASURE_COUNT, itemCount)
                intent.putExtra(TREASURE_OBJECT, treasure)
                startActivity(intent)
            }
            R.id.btnOk -> {
                logInf(MAIN_LOGGER_TAG, "\"OK\" button was clicked")
                dismiss()
            }
        }
    }
}