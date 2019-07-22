package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class CoinFlipUsersFragment : Fragment() {

    private lateinit var linearUsers: LinearLayout

    companion object {
        fun newInstance() = CoinFlipUsersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_coin_flip_users, container, false)

        linearUsers = v.findViewById(R.id.linearUsers)
        linearUsers.setOnClickListener {
            logInf(MAIN_LOGGER_TAG, "WORK!")
        }

        return v
    }
}