package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero

import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item

interface FindHeroGame {

    fun winTurn(position: Int)

    fun winGame(winningItem: Item)

    fun loseGame()
}