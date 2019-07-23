package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import android.content.Context
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.NicknameCreator
import java.util.*

class BotCreator(context: Context) {

    private val nicknameCreator = NicknameCreator(context)

    companion object {
        private val random = Random()
        private val teamList = listOf(
            "Alliance", "Chaos Esports", "Evil Geniuses", "Fnatic", "Forward Gaming", "Infamous", "Keen Gaming",
            "LGD Gaming", "Mineski", "NAVI", "Ninjas in Pyjams", "OG", "Royal Never Give Up", "Team Liquid",
            "Team Secret", "TNC", "Vici Gaming", "Virtus Pro"
        )
    }

    fun createNickname(): String {
        return nicknameCreator.createSingleNickname()
    }

    fun createCountOfItem(): Int {
        return 1 + (Math.random() * 5).toInt()
    }

    fun createChance(playerMoney: Float, botMoney: Float): Float {
        return botMoney / (playerMoney + botMoney) * 100
    }

    fun createMoney(playerMoney: Float): Float {
        val intPart = playerMoney - (Math.random() * (playerMoney / 4).toInt()).toInt()
        val realPart = Math.random()
        return (intPart + realPart).toFloat()
    }

    fun createImgTeamId(playerTeamName: String?): Int {
        val randNum = random.nextInt(teamList.size)
        val randName = teamList[randNum]

        return if (playerTeamName != randName) {
            getTeamIdFromName(randName)
        } else {
            createImgTeamId(playerTeamName)
        }
    }
}