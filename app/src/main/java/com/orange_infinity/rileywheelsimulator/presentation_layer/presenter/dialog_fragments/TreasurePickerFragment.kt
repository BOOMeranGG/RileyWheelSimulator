package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments

import android.app.Activity
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
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
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
        btnSell = v.findViewById(R.id.btnSell)
        btnOk = v.findViewById(R.id.btnOk)

        btnSell.setOnClickListener(this)
        btnSell.text = "Open"
        btnOk.setOnClickListener(this)

        imgItem.setImageDrawable(
            IconController.getInstance(context?.applicationContext)
                .getItemIconDrawableWithBox(treasure)
        )

        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(setTitleName(treasure))
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

    override fun onDestroy() {
        super.onDestroy()
        sendResult()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.darker_gray)
    }

    private fun sendResult() {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent())
    }

    private fun setTitleName(item: Item): String {
        return "${item.getItemName()}: ${item.getRarity()}"
    }
}