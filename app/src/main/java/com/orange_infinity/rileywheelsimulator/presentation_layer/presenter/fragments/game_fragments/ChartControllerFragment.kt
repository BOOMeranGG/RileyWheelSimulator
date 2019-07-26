package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.ChartActivity

private const val PLACE_BET = "Place bet"
private const val STOP = "Stop"
private const val CONTINUE = "Continue"
private const val SKIP = "Skip"

class ChartControllerFragment : Fragment(), View.OnClickListener {

    private lateinit var btnController: Button
    private lateinit var btnCasino: Button
    private lateinit var btnListener: OnControllerButtonClicked

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


        return v
    }

    override fun onClick(v: View?) {
        when {
            btnController.text == PLACE_BET -> {
                btnController.text = STOP
                btnListener.onPlaceBet()
            }
            btnController.text == STOP -> {
                btnController.text = CONTINUE
                btnListener.onStopBet()
            }
            btnController.text == CONTINUE -> {
                btnController.text = PLACE_BET
                btnListener.onContinue()
            }
        }
    }

    fun onLose() {
        btnController.text = CONTINUE
    }

    interface OnControllerButtonClicked {

        fun onPlaceBet()

        fun onStopBet()

        fun onContinue()
    }
}