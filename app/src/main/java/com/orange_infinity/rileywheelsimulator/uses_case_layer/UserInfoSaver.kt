package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.entities_layer.User
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences

class UserInfoSaver(private val context: Context, private val preferences: UserPreferences) {

    fun saveNickname(nickName: String) {
        User.nickname = nickName
        preferences.saveNickname(context, nickName)
    }

    fun saveBattlePassExp() {
        TODO("not implemented")
    }

    fun saveTotalInventoryCost() {
        TODO("not implemented")
    }
}