package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core

interface TechiesGame {

    fun winTurn(position: Int)

    fun winGame()

    fun lose(position: Int)
}