package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core

private val stageCoef = listOf(1.0f, 1.2f, 1.4f, 1.6f, 2.0f, 2.5f, 3.0f, 3.5f, 4.0f, 5.0f, 6.0f)
private const val ROW = 5

class TechiesEngine(
    private val game: TechiesGame,
    private val column: Int
) {

    private val count = ROW * column
    private var gameStage = 1
    var currentPrizePool: Float = 0.0f

    fun clickOnCell(position: Int, rate: Int) {
        if (position % column != gameStage) { // If click in other line
            return
        }
        if (randomIsPlayerWinner()) {
            currentPrizePool = rate * stageCoef[gameStage]
            if (gameStage == column) {
                game.winGame()
            } else {
                gameStage++
                game.winTurn()
            }
        } else {
            game.lose()
        }
    }

    fun getMinesArray(position: Int, isAlive: Boolean): List<Boolean> {
        var countOfMines = 4
        var fieldMines = mutableListOf<Boolean>()

        if (!isAlive) {
            fieldMines[position - 1] = true
            countOfMines--
        }
        while (countOfMines != 0) {
            //TODO("REALIZE")
        }

        return fieldMines
    }

    private fun randomIsPlayerWinner(): Boolean {
        val randNum = 1 + (Math.random() * 5).toInt()
        return randNum == 1
    }
}