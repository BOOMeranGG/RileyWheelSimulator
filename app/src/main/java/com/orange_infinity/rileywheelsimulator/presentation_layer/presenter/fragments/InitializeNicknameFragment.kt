package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.MainActivity
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoSaver
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class InitializeNicknameFragment : Fragment(), View.OnClickListener {

    private lateinit var editNickname: EditText
    private lateinit var btnNext: Button

    companion object {
        fun newInstance(): InitializeNicknameFragment =
            InitializeNicknameFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO("Если имя пользователя уже задано, то открываем другое окно, а это закрываем")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.initialize_nickname_fragment, container, false)
        editNickname = v.findViewById(R.id.editNickname)
        btnNext = v.findViewById(R.id.btnNext)
        btnNext.setOnClickListener(this)

        return v
    }

    override fun onClick(v: View) {
        val nick = editNickname.text.toString()

//        if (nick.isEmpty()) {
//            //TODO("Сообщить пользователю об ошибке")
//            return
//        }
        activity?.let {
            UserInfoSaver(it, UserPreferencesImpl()).saveNickname(nick)
        }
        goOnNextActivity()
    }

    private fun goOnNextActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        logInf(MAIN_LOGGER_TAG, "Start MainActivity (TopButtonFragment + RileyWheelFragment)")
    }
}