package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_EXPERIANCE_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_PLAYER_MONEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_USERNAME_KEY
import com.orange_infinity.rileywheelsimulator.entities_layer.User
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences

class UserInfoController(
    private val context: Context?,
    private val preferences: UserPreferences,
    private val inventoryRepository: InventoryRepository
) {

    fun saveNickname(nickName: String) {
        User.nickname = nickName
        preferences.saveStringInPreferences(context, nickName, PREFERENCES_USERNAME_KEY)
    }

    fun getNickname(): String {
        if (User.nickname == null) {
            User.nickname = preferences.getStringFromPreferences(context, PREFERENCES_USERNAME_KEY)
        }
        return User.nickname + ""
    }

    fun changeUserMoney(itemCost: Float) {
        val currentMoney = getUserMoney()
        val newMoney = currentMoney + itemCost
        preferences.saveFloatInPreferences(context, newMoney, PREFERENCES_PLAYER_MONEY)
    }

    fun getUserMoney(): Float {
        return preferences.getFloatFromPreferences(context, PREFERENCES_PLAYER_MONEY) ?: 0f
    }

    fun getTotalItemCost(): Float {
        val items = inventoryRepository.getItemsFromInventory()
        var totalCost = 0f
        items.forEach { totalCost += it.key.getCost() * it.value }
        return totalCost
    }

    fun getCountOfItems(): Int {
        val items = inventoryRepository.getItemsFromInventory()
        var count = 0
        items.forEach { count += it.value }
        return count
    }

    fun addBattlePassExp(exp: Int) {
        val currentExp = getBattlePassExp()
        preferences.saveIntInPreferences(context, exp, PREFERENCES_EXPERIANCE_KEY)
    }

    fun getBattlePassExp(): Int {
        return preferences.getIntFromPreferences(context, PREFERENCES_EXPERIANCE_KEY) ?: 0
    }
}