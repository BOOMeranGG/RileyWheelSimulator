package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.techies

import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.TechiesGame
import com.orange_infinity.rileywheelsimulator.util.CASINO_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

private val stageCoef = listOf(1.0f, 1.2f, 1.4f, 1.6f, 2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 5.0f, 6.0f)

class TechiesEngine(
    private val game: TechiesGame,
    private val column: Int
) {

    var gameStage = 0
    private var currentPrizePool: Float = 0.0f
    private var firstRate: Float = 0.0f
    var mineIndex: Int = 0

    fun clickOnCell(position: Int, rate: Float) {
        if (position % column != gameStage) { // If click in other line
            return
        }
        logInf(CASINO_LOGGER_TAG, "ClickOnCell started")
        if (currentPrizePool == 0f) {
            currentPrizePool = rate
            firstRate = rate
        }
        gameStage++

        mineIndex = getMinePosition()
        if (mineIndex != position) {
            currentPrizePool *= stageCoef[gameStage]
            if (gameStage == column) {
                game.winGame()
            } else {
                game.winTurn(position)
            }
        } else {
            game.lose(position)
        }
        logInf(CASINO_LOGGER_TAG, "ClickOnCell ended. Current prize pool = $currentPrizePool")
    }

    fun prepareNewGame() {
        gameStage = 0
        currentPrizePool = 0.0f
    }

    fun getMinePosition(): Int {
        val cellNumber = (Math.random() * 5).toInt()
        var minePosition = gameStage - 1

        if (cellNumber == 0)
            return minePosition
        return minePosition + cellNumber * column
    }

    fun getPrize(): Float {
        return firstRate * stageCoef[gameStage]
    }
}