package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.support.v4.app.Fragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.InitializeNicknameFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.SingleFragmentActivity

class InitializeNicknameActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment =
        InitializeNicknameFragment.newInstance()
}
