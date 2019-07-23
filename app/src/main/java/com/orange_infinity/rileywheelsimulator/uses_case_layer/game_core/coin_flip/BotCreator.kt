package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import java.util.*

class BotCreator(val playerCost: Float) {

    companion object {

        private val random = Random()
        private val teamList = listOf(
            "Alliance", "Chaos Esports", "Evil Geniuses", "Fnatic", "Forward Gaming", "Infamous", "Keen Gaming",
            "LGD Gaming", "Mineski", "NAVI", "Ninjas in Pyjams", "OG", "Royal Never Give Up", "Team Liquid",
            "Team Secret", "TNC", "Vici Gaming", "Virtus Pro"
        )
    }

    fun createNickname(): String {
        return "Test nick"
    }

    fun createCountOfItem(): Int {
        return 2
    }

    fun createMoney(): Float {
        return 22.0f
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