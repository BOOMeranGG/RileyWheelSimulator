package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_COUNT_OF_ITEM_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_EXPERIANCE_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_ITEM_COST_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_USERNAME_KEY
import com.orange_infinity.rileywheelsimulator.entities_layer.User
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences

class UserInfoSaver(private val context: Context?, private val preferences: UserPreferences) {

    fun saveNickname(nickName: String) {
        User.nickname = nickName
        preferences.saveStringInPreferences(context, nickName, PREFERENCES_USERNAME_KEY)
    }

    fun plusBattlePassExp(exp: Int) {
        preferences.saveIntInPreferences(context, (getBattlePassExp() + exp), PREFERENCES_EXPERIANCE_KEY)
    }

    fun plusTotalItemCost(cost: Int) {
        preferences.saveTotalItemCost(context, (getTotalItemCost() + cost), cost)
    }

    fun getNickname(): String {
        if (User.nickname == null) {
            User.nickname = preferences.getStringFromPreferences(context, PREFERENCES_USERNAME_KEY)
        }
        return User.nickname + ""
    }

    fun getBattlePassExp(): Int = preferences.getIntFromPreferences(context, PREFERENCES_EXPERIANCE_KEY) ?: 0

    fun getTotalItemCost(): Int = preferences.getIntFromPreferences(context, PREFERENCES_ITEM_COST_KEY) ?: 0

    fun getCountOfItems(): Int = preferences.getIntFromPreferences(context, PREFERENCES_COUNT_OF_ITEM_KEY) ?: 0
}