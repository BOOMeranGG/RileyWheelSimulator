package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_EXPERIANCE_KEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_PLAYER_MONEY
import com.orange_infinity.rileywheelsimulator.data_layer.PREFERENCES_USERNAME_KEY
import com.orange_infinity.rileywheelsimulator.entities_layer.Grade
import com.orange_infinity.rileywheelsimulator.entities_layer.User
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.UserPreferences
import java.lang.RuntimeException

class UserInfoController(
    private val context: Context?,
    private val preferences: UserPreferences,
    private val inventoryRepository: InventoryRepository
) {

    fun getCurrentGrade(): Grade {
        if (User.grade == null) {
            calculateGrade()
        }
        return User.grade!!
    }

    fun getCurrentLevel(): Int {
        if (User.level == null) {
            getCurrentGrade()
        }
        return User.level!!
    }

    fun saveNickname(nickName: String) {
        User.nickname = nickName
        preferences.saveStringInPreferences(context, nickName, PREFERENCES_USERNAME_KEY)
    }

    fun getNickname(): String {
        if (User.nickname == null) {
            User.nickname = preferences.getStringFromPreferences(context, PREFERENCES_USERNAME_KEY)
        }
        return "${User.nickname}"
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
        preferences.saveIntInPreferences(context, currentExp + exp, PREFERENCES_EXPERIANCE_KEY)
    }

    fun getBattlePassExp(): Int {
        return preferences.getIntFromPreferences(context, PREFERENCES_EXPERIANCE_KEY) ?: 0
    }

    private fun calculateGrade() {
        val currentExp = getBattlePassExp()
        User.level = calculateLevel(currentExp)

        when (User.level) {
            in 1 until 70 -> {
                User.grade = Grade.HERALD
                User.grade!!.countOfStars = (User.level!! / 10) + 1
            }
            in 70 until 210 -> {
                User.grade = Grade.GUARDIAN
                User.grade!!.countOfStars = ((User.level!! - 70) / 20) + 1
            }
            in 210 until 420 -> {
                User.grade = Grade.CRUSADER
                User.grade!!.countOfStars = ((User.level!! - 210) / 30) + 1
            }
            in 420 until 700 -> {
                User.grade = Grade.ARCHON
                User.grade!!.countOfStars = ((User.level!! - 420) / 40) + 1
            }
            in 700 until 1051 -> {
                User.grade = Grade.LEGEND
                User.grade!!.countOfStars = ((User.level!! - 700) / 50) + 1
            }
            in 1050 until 1470 -> {
                User.grade = Grade.ANCIENT
                User.grade!!.countOfStars = ((User.level!! - 1050) / 60) + 1
            }
            else -> {
                User.grade = Grade.DIVINE
                User.grade!!.countOfStars = ((User.level!! - 1470) / 70) + 1
            }
        }
    }

    private fun calculateLevel(currentExp: Int): Int {
        return currentExp / 100
    }
}