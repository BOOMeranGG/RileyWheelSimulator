package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.techies

interface TechiesGame {

    fun winTurn(position: Int)

    fun winGame()

    fun loseGame(position: Int)
}