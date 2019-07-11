package com.orange_infinity.rileywheelsimulator.data_layer.db

import android.content.Context
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val FILE_SETTINGS_NAME = "settingsFileName"
private const val USERNAME_KEY = "username"
private const val EXPERIANCE_KEY = "experiance"
private const val INVENTORY_COST_KEY = "inventoryCost"

class UserPreferencesImpl: UserPreferences {

    override fun saveNickname(context: Context, nickname: String) {
        saveStringInPreferences(context, nickname, USERNAME_KEY)
    }

    override fun saveBattlePassExp(context: Context, exp: String) {
        saveStringInPreferences(context, exp, EXPERIANCE_KEY)
    }

    override fun saveTotalInventoryCost(context: Context, cost: String) {
        saveStringInPreferences(context, cost, INVENTORY_COST_KEY)
    }

    private fun saveStringInPreferences(context: Context, info: String, tag: String) {
        val settings = context.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(tag, info)
        editor.apply()

        logInf(MAIN_LOGGER_TAG, "Preferences with tag \"$tag\" and info \"$info\" was created")
    }
}