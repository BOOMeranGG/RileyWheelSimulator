package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoSaver

class AccountFragment : Fragment() {

    private lateinit var tvNickname: TextView

    companion object {
        fun newInstance(): AccountFragment = AccountFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.account_fragment, container, false)

        val infoSaver = UserInfoSaver(activity, UserPreferencesImpl())
        val nick = infoSaver.getNickname()
        val totalCost = infoSaver.getTotalItemCost()
        val itemCount = infoSaver.getCountOfItems()

        tvNickname = v.findViewById(R.id.tvNickname)
        tvNickname.text = "$nick\nTotal item cost = $totalCost$\nItem count = $itemCount"

        return v
    }
}