package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero

interface FindHeroGame {

    fun winTurn(position: Int)

    fun winGame()

    fun loseGame()
}