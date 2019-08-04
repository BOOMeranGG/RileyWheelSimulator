package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import com.orange_infinity.rileywheelsimulator.R

class FindPickerRuleFragment : DialogFragment() {


    companion object {
        fun newInstance() = FindPickerRuleFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.fragment_rule, null)


        return AlertDialog.Builder(activity)
            .setView(v)
            .setCancelable(true)
            .create()
    }
}