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
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.CoinFlipRoomActivity
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.IMAGE_ID_KEY
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities.TEAM_NAME_KEY
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.lang.RuntimeException

const val TOTAL_COST_KEY = "totalCostKey"
const val PICKED_LIST_NAMING = "pickedList"

class TeamPickerFragment : DialogFragment(), View.OnClickListener {

    private lateinit var btnLeave: Button
    private lateinit var btnSelect: Button
    private lateinit var imgTeam: ImageView
    private lateinit var teamListId: List<Int>

    private var currentTeamImgIndex: Int = 0
    private var pickNamingList: ArrayList<String>? = null
    private var totalCost: Float = 0f

    companion object {
        fun newInstance() = TeamPickerFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_team_pick, null)

        val args = arguments!!
        totalCost = args.getFloat(TOTAL_COST_KEY)
        pickNamingList = args.getStringArrayList(PICKED_LIST_NAMING)

        init(v)

        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle("")
            .setCancelable(true)
            .create()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgTeam) {
            setNextTeamIcon()
        } else if (v.id == R.id.btnSelect){
            val teamName = getTeamNameFromIndex(currentTeamImgIndex)
            val imgId = teamListId[currentTeamImgIndex]
            val intent = Intent(activity, CoinFlipRoomActivity::class.java)
            intent.putExtra(TEAM_NAME_KEY, teamName)
            intent.putExtra(IMAGE_ID_KEY, imgId)
            intent.putExtra(TOTAL_COST_KEY, totalCost)
            intent.putStringArrayListExtra(PICKED_LIST_NAMING, pickNamingList)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)

            startActivity(intent)
            dismiss()
        }
    }

    private fun setNextTeamIcon() {
        if (currentTeamImgIndex < teamListId.size - 1) {
            currentTeamImgIndex++
        } else {
            currentTeamImgIndex = 0
        }
        imgTeam.setImageResource(teamListId[currentTeamImgIndex])
        logInf(MAIN_LOGGER_TAG, "Next team")
    }

    private fun init(v: View) {
        btnLeave = v.findViewById(R.id.btnLeave)
        btnLeave.setOnClickListener {
            dismiss()
        }
        btnSelect = v.findViewById(R.id.btnSelect)
        btnSelect.setOnClickListener(this)

        imgTeam = v.findViewById(R.id.imgTeam)
        imgTeam.setOnClickListener(this)

        teamListId = listOf(
            R.drawable.navi, R.drawable.team_secret, R.drawable.evil_geniuses,
            R.drawable.virtus_pro, R.drawable.team_liquid, R.drawable.infamous,
            R.drawable.keen_gaming, R.drawable.lgd_gaming, R.drawable.mineski,
            R.drawable.alliance, R.drawable.ninjas_in_pyjams, R.drawable.og,
            R.drawable.royal_ngu, R.drawable.forward_gaming, R.drawable.chaos_esports,
            R.drawable.tnc, R.drawable.vici_gaming, R.drawable.fnatic
        )

        imgTeam.setImageResource(teamListId.first())
        currentTeamImgIndex = 0
    }

    private fun getTeamNameFromIndex(index: Int): String {
        when (teamListId[index]) {
            R.drawable.alliance -> return "Alliance"
            R.drawable.chaos_esports -> return "Chaos Esports"
            R.drawable.evil_geniuses -> return "Evil Geniuses"
            R.drawable.fnatic -> return "Fnatic"
            R.drawable.forward_gaming -> return "Forward Gaming"
            R.drawable.infamous -> return "Infamous"
            R.drawable.keen_gaming -> return "Keen Gaming"
            R.drawable.lgd_gaming -> return "LGD Gaming"
            R.drawable.mineski -> return "Mineski"
            R.drawable.navi -> return "NAVI"
            R.drawable.ninjas_in_pyjams -> return "Ninjas in Pyjams"
            R.drawable.og -> return "OG"
            R.drawable.royal_ngu -> return "Royal Never Give Up"
            R.drawable.team_liquid -> return "Team Liquid"
            R.drawable.team_secret -> return "Team Secret"
            R.drawable.tnc -> return "TNC"
            R.drawable.vici_gaming -> return "Vici Gaming"
            R.drawable.virtus_pro -> return "Virtus Pro"
            else -> throw RuntimeException()
        }
    }
}