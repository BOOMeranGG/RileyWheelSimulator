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
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.InventoryController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class ItemPickerFragment : DialogFragment(), View.OnClickListener {

    private lateinit var imgItem: ImageView
    private lateinit var tvCost: TextView
    private lateinit var tvItemName: TextView
    private lateinit var tvRarity: TextView
    private lateinit var btnSell: Button
    private lateinit var btnOk: Button
    private lateinit var inventoryController: InventoryController

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

        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(context?.applicationContext))

        imgItem = v.findViewById(R.id.imgItem)
        tvCost = v.findViewById(R.id.tvCost)
        tvItemName = v.findViewById(R.id.tvItemName)
        tvRarity = v.findViewById(R.id.tvRarity)
        btnSell = v.findViewById(R.id.btnSell)
        btnOk = v.findViewById(R.id.btnOk)

        btnSell.setOnClickListener(this)
        btnOk.setOnClickListener(this)

        imgItem.setImageDrawable(IconController.getInstance(context?.applicationContext).getItemIconDrawable(item))
        tvCost.text = "${item.getCost()}$"
        tvItemName.text = item.getItemName()
        tvRarity.text = item.getRarity()

        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(setTitleName(item))
            .setCancelable(true)
            .create()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSell -> {
                logInf(MAIN_LOGGER_TAG, "\"Sell\" button was clicked")
                //TODO("Деньги с продажи должны переходить в опыт компендиума")
                inventoryController.deleteItem(item)
                itemCount--
                if (itemCount == 0) {
                    dismiss()
                    sendResult()
                }
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

    //TODO("В Title задавать название героя, если оно есть, иначе ---> что за предмет")
    private fun setTitleName(item: Item): String {
        return "Item info"
    }

    private fun sendResult() {
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, Intent())
    }
}