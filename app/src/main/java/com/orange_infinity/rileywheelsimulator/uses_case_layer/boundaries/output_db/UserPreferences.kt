package com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db

import android.content.Context

interface UserPreferences {

    fun saveNickname(context: Context, nickname: String)

    fun saveBattlePassExp(context: Context, exp: String)

    fun saveTotalInventoryCost(context: Context, cost: String)
}