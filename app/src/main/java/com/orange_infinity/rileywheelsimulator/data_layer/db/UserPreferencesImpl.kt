package com.orange_infinity.rileywheelsimulator.data_layer.db

import android.content.Context
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val FILE_SETTINGS_NAME = "settingsFileName"
private const val USERNAME_KEY = "username"
private const val EXPERIANCE_KEY = "experiance"
private const val INVENTORY_COST_KEY = "inventoryCost"
private const val COUNT_OF_ITEM_KEY = "countOfItem"

class UserPreferencesImpl : UserPreferences {

    override fun saveNickname(context: Context?, nickname: String) {
        saveStringInPreferences(context, nickname, USERNAME_KEY)
    }

    override fun saveBattlePassExp(context: Context?, exp: Int) {
        saveIntInPreferences(context, exp, EXPERIANCE_KEY)
    }

    override fun saveTotalItemCost(context: Context?, cost: Int) {
        saveIntInPreferences(context, cost, INVENTORY_COST_KEY)
        val newCount = getIntFromPreferences(context, COUNT_OF_ITEM_KEY)!!.plus(
            if (cost > 0) {
                1
            } else {
                -1
            }
        )
        saveIntInPreferences(context, newCount, COUNT_OF_ITEM_KEY)
    }

    override fun getNickname(context: Context?): String {
        return getStringFromPreferences(context, USERNAME_KEY) ?: return ""
    }

    override fun getBattlePassExp(context: Context?): Int {
        return getIntFromPreferences(context, EXPERIANCE_KEY) ?: return 0
    }

    override fun getTotalInventoryCost(context: Context?): Int {
        return getIntFromPreferences(context, INVENTORY_COST_KEY) ?: return 0
    }

    override fun getCountOfItems(context: Context?): Int {
        return getIntFromPreferences(context, COUNT_OF_ITEM_KEY) ?: return 0
    }

    private fun saveStringInPreferences(context: Context?, info: String, tag: String) {
        val settings = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings?.edit()
        editor?.putString(tag, info)
        editor?.apply()

        logInf(MAIN_LOGGER_TAG, "Preferences with tag \"$tag\" and info \"$info\" was created")
    }

    private fun saveIntInPreferences(context: Context?, info: Int, tag: String) {
        val settings = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings?.edit()
        editor?.putInt(tag, info)
        editor?.apply()

        logInf(MAIN_LOGGER_TAG, "Preferences with tag \"$tag\" and info \"$info\" was created")
    }

    private fun getStringFromPreferences(context: Context?, tag: String): String? {
        val preferences = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        return preferences?.getString(tag, "")
    }

    private fun getIntFromPreferences(context: Context?, tag: String): Int? {
        val preferences = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        return preferences?.getInt(tag, 0)
    }
}