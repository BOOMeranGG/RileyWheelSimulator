package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.ChartActivity
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController

private const val PLACE_BET = "Place bet"
private const val STOP = "Stop"
private const val CONTINUE = "Continue"
private const val SKIP = "Skip"

class ChartControllerFragment : Fragment(), View.OnClickListener {

    private lateinit var userInfoController: UserInfoController
    private lateinit var btnController: Button
    private lateinit var btnCasino: Button
    private lateinit var btnListener: OnControllerButtonClicked
    private lateinit var btnAddTen: Button
    private lateinit var btnAddFifty: Button
    private lateinit var btnAddHundred: Button

    private var currentBet: Float = 0f

    companion object {
        fun newInstance() = ChartControllerFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        btnListener = context as ChartActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_chart_controller, container, false)

        btnController = v.findViewById(R.id.btnController)
        btnCasino = v.findViewById(R.id.btnCasino)
        btnCasino.setOnClickListener {
            activity?.finish()
        }
        btnController.setOnClickListener(this)


        btnAddTen = v.findViewById(R.id.btnAddTen)
        btnAddFifty = v.findViewById(R.id.btnAddFifty)
        btnAddHundred = v.findViewById(R.id.btnAddHundred)
        btnAddTen.setOnClickListener(this)
        btnAddFifty.setOnClickListener(this)
        btnAddHundred.setOnClickListener(this)

        userInfoController = UserInfoController(
            activity, UserPreferencesImpl(), InventoryRepositoryImpl.getInstance(activity?.applicationContext)
        )

        return v
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View) {
        val restOfMoney = userInfoController.getUserMoney() - currentBet
        when (v.id) {
            btnAddTen.id -> {
                if (restOfMoney < 10)
                    return
                currentBet+= 10
                btnController.text = "$PLACE_BET $currentBet$"
            }
            btnAddFifty.id -> {
                if (restOfMoney < 50)
                    return
                currentBet += 50
                btnController.text = "$PLACE_BET $currentBet$"
            }
            btnAddHundred.id -> {
                if (restOfMoney < 100)
                    return
                currentBet += 100
                btnController.text = "$PLACE_BET $currentBet$"
            }
            btnController.id -> changeButtonText()
        }
    }

    fun onLose() {
        btnController.text = CONTINUE
    }

    private fun isAddMoneyBtnEnabled(isEnable: Boolean) {
        btnAddTen.isEnabled = isEnable
        btnAddFifty.isEnabled = isEnable
        btnAddHundred.isEnabled = isEnable
    }

    private fun changeButtonText() {
        when {
            btnController.text.contains(PLACE_BET) -> {
                btnController.text = STOP
                isAddMoneyBtnEnabled(false)
                btnListener.onPlaceBet(currentBet)
            }
            btnController.text.contains(STOP) -> {
                btnController.text = CONTINUE
                btnListener.onStopBet()
            }
            btnController.text.contains(CONTINUE) -> {
                btnController.text = PLACE_BET
                isAddMoneyBtnEnabled(true)
                btnListener.onContinue()
                currentBet = 0f
            }
        }
    }

    interface OnControllerButtonClicked {

        fun onPlaceBet(betMoney: Float)

        fun onStopBet()

        fun onContinue()
    }
}