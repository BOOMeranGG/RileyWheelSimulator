package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

class GameController(private val botAccount: BotAccount) {
    //50 --> 5000  99 --> 9900  45.54 --> 4554 | | 0..10000
    fun isPlayerWinner(): Boolean {
        val botChance = (botAccount.chance * 100).toInt()
        val randNum = (Math.random() * 10000).toInt()
        return randNum > botChance
    }
}