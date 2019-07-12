package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.entities_layer.User
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences

class UserInfoSaver(private val context: Context?, private val preferences: UserPreferences) {

    fun saveNickname(nickName: String) {
        User.nickname = nickName
        preferences.saveNickname(context, nickName)
    }

    fun plusBattlePassExp(exp: Int) {
        preferences.saveBattlePassExp(context, (getBattlePassExp() + exp))
    }

    fun plusTotalItemCost(cost: Int) {
        preferences.saveTotalItemCost(context, (getTotalItemCost() + cost))
    }

    fun getNickname(): String {
        if (User.nickname == null) {
            User.nickname = preferences.getNickname(context)
        }
        return User.nickname + ""
    }

    fun getBattlePassExp(): Int = preferences.getBattlePassExp(context)

    fun getTotalItemCost(): Int = preferences.getTotalInventoryCost(context)

    fun getCountOfItems(): Int = preferences.getCountOfItems(context)
}