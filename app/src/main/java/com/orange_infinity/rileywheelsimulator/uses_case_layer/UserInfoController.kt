package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_COUNT_OF_ITEM_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_EXPERIANCE_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_ITEM_COST_KEY
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

    fun getBattlePassExp(): Int {
        return 0
    }

    //fun getBattlePassExp(): Int = preferences.getIntFromPreferences(context, PREFERENCES_EXPERIANCE_KEY) ?: 0

    //fun getTotalItemCost(): Int = preferences.getIntFromPreferences(context, PREFERENCES_ITEM_COST_KEY) ?: 0

    //fun getCountOfItems(): Int = preferences.getIntFromPreferences(context, PREFERENCES_COUNT_OF_ITEM_KEY) ?: 0
}