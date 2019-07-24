package com.orange_infinity.rileywheelsimulator.data_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val FILE_SETTINGS_NAME = "settingsFileName"
const val PREFERENCES_ITEM_COST_KEY = "inventoryCost"
const val PREFERENCES_COUNT_OF_ITEM_KEY = "countOfItem"
const val PREFERENCES_USERNAME_KEY = "username"
const val PREFERENCES_PLAYER_MONEY = "totalPlayerMoney"
const val PREFERENCES_EXPERIANCE_KEY = "experiance"

class UserPreferencesImpl : UserPreferences {

    override fun saveTotalItemCost(context: Context?, cost: Int, add: Int) {
        saveIntInPreferences(context, cost, PREFERENCES_ITEM_COST_KEY)
        val newCount = getIntFromPreferences(context,
            PREFERENCES_COUNT_OF_ITEM_KEY
        )!!.plus(
            if (add > 0) {
                1
            } else {
                -1
            }
        )
        saveIntInPreferences(context, newCount, PREFERENCES_COUNT_OF_ITEM_KEY)
    }

    override fun saveFloatInPreferences(context: Context?, info: Float, tag: String) {
        val settings = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings?.edit()
        editor?.putFloat(tag, info)
        editor?.apply()

        logInf(MAIN_LOGGER_TAG, "Preferences with tag \"$tag\" and info \"$info\" was created")
    }

    override fun getFloatFromPreferences(context: Context?, tag: String): Float? {
        val preferences = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        return preferences?.getFloat(tag, 0f)
    }

    override fun saveStringInPreferences(context: Context?, info: String, tag: String) {
        val settings = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings?.edit()
        editor?.putString(tag, info)
        editor?.apply()

        logInf(MAIN_LOGGER_TAG, "Preferences with tag \"$tag\" and info \"$info\" was created")
    }

    override fun saveIntInPreferences(context: Context?, info: Int, tag: String) {
        val settings = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        val editor = settings?.edit()
        editor?.putInt(tag, info)
        editor?.apply()

        logInf(MAIN_LOGGER_TAG, "Preferences with tag \"$tag\" and info \"$info\" was created")
    }

    override fun getStringFromPreferences(context: Context?, tag: String): String? {
        val preferences = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        return preferences?.getString(tag, "")
    }

    override fun getIntFromPreferences(context: Context?, tag: String): Int? {
        val preferences = context?.getSharedPreferences(FILE_SETTINGS_NAME, Context.MODE_PRIVATE)
        return preferences?.getInt(tag, 0)
    }
}