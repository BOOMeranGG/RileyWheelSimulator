package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero

import com.orange_infinity.rileywheelsimulator.entities_layer.items.Arcana
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Courier
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Treasure

class FindHeroEngine(private val game: FindHeroGame) {

    var gameField = arrayOf<Item?>(null, null, null, null, null, null, null, null, null)
    private var turn = 0
    private lateinit var firstItemPicked: Item

    fun newGame() {
        createField()
        turn = 0
    }

    fun pick(position: Int) {
        if (turn == 0) {
            turn++
            firstItemPicked = gameField[position]!!
            game.winTurn(position)
            return
        }
        turn++
        if (gameField[position] != firstItemPicked) {
            game.loseGame()
            return
        }
        if (turn != 3) {
            game.winTurn(position)
        } else {
            game.winTurn(position)
        }
    }

    private fun createField() {
        val arcana = Arcana.BladesOfVothDomosh.getRandomItem()
        val courier = Courier.AlphidOfLecaciida.getRandomItem()
        val treasure = Treasure.GenuineTreasureOfTheShatteredHourglass.getRandomItem()

        setRandomItemPosition(arcana)
        setRandomItemPosition(courier)
        setRandomItemPosition(treasure)
    }

    private fun setRandomItemPosition(item: Item) {
        var count = 3

        while (count != 0) {
            for (i in 0..8) {
                if (gameField[i] != null) {
                    continue
                }
                if ((Math.random() * 9).toInt() == 1) {
                    gameField[i] = item
                    count--
                    if (count == 0) {
                        break
                    }
                }
            }
        }
    }
}