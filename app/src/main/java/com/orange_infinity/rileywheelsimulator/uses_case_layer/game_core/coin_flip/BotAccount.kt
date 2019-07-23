package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip

class BotAccount(playerCost: Float = 0.0f, playerTeamName: String?) {

    private val botCreator = BotCreator(playerCost)
    var chance = 50.0f
    val nickname: String
    val countOfItems: Int
    val money: Float
    val imgTeamId: Int

    init {
        nickname = botCreator.createNickname()
        countOfItems = botCreator.createCountOfItem()
        money = botCreator.createMoney()
        imgTeamId = botCreator.createImgTeamId(playerTeamName)
    }
}