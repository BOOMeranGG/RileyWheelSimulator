package com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db

import android.content.Context

interface UserPreferences {

    fun saveNickname(context: Context?, nickname: String)

    fun saveBattlePassExp(context: Context?, exp: Int)

    fun saveTotalItemCost(context: Context?, cost: Int)

    fun getNickname(context: Context?): String

    fun getBattlePassExp(context: Context?): Int

    fun getTotalInventoryCost(context: Context?): Int

    fun getCountOfItems(context: Context?): Int
}