package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

import android.content.Context
import java.io.Serializable

class BotAccount(playerMoney: Float = 0.0f, playerTeamName: String?, context: Context) : Serializable {

    private val botCreator = BotCreator(context)
    var chance = 50.0f
    val nickname: String
    val countOfItems: Int
    val money: Float
    val imgTeamId: Int

    init {
        nickname = botCreator.createNickname()
        countOfItems = botCreator.createCountOfItem()
        money = botCreator.createMoney(playerMoney)
        imgTeamId = botCreator.createImgTeamId(playerTeamName)
        chance = botCreator.createChance(playerMoney, money)
    }
}