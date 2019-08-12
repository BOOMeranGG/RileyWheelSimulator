package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import com.orange_infinity.rileywheelsimulator.R
import java.lang.RuntimeException

fun getTeamNameFromIndex(id: Int): String {
    when (id) {
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

fun getTeamIdFromName(teamName: String) : Int {
    when (teamName) {
        "Alliance" -> return R.drawable.alliance
        "Chaos Esports" -> return R.drawable.chaos_esports
        "Evil Geniuses" -> return R.drawable.evil_geniuses
        "Fnatic" -> return R.drawable.fnatic
        "Forward Gaming" -> return R.drawable.forward_gaming
        "Infamous" -> return R.drawable.infamous
        "Keen Gaming" -> return R.drawable.keen_gaming
        "LGD Gaming" -> return R.drawable.lgd_gaming
        "Mineski" -> return R.drawable.mineski
        "NAVI" -> return R.drawable.navi
        "Ninjas in Pyjams" -> return R.drawable.ninjas_in_pyjams
        "OG" -> return R.drawable.og
        "Royal Never Give Up" -> return R.drawable.royal_ngu
        "Team Liquid" -> return R.drawable.team_liquid
        "Team Secret" -> return R.drawable.team_secret
        "TNC" -> return R.drawable.tnc
        "Vici Gaming" -> return R.drawable.vici_gaming
        "Virtus Pro" -> return R.drawable.virtus_pro
        else -> throw RuntimeException()
    }
}