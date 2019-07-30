package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero

import com.orange_infinity.rileywheelsimulator.entities_layer.items.Arcana
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Courier
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Treasure

class FindHeroEngine(private val game: FindHeroGame) {

    private lateinit var firstItemPicked: Item
    private lateinit var arcana: Arcana
    private lateinit var courier: Courier
    private lateinit var treasure: Treasure

    var gameField = arrayOf<Item?>(null, null, null, null, null, null, null, null, null)
    var fieldVisited = arrayOf(false, false, false, false, false, false, false, false, false)
    private var turn = 0

    fun newGame() {
        createField()
        turn = 0
    }

    fun pick(position: Int) {
        if (turn == 0) {
            turn++
            firstItemPicked = gameField[position]!!
            fieldVisited[position] = true
            game.winTurn(position)
            return
        }
        if (fieldVisited[position]) {
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
            game.winGame(firstItemPicked)
        }
        fieldVisited[position] = true
    }

    private fun createField() {
        arcana = Arcana.BladesOfVothDomosh.getRandomItem() as Arcana
        courier = Courier.AlphidOfLecaciida.getRandomItem() as Courier
        treasure = Treasure.GenuineTreasureOfTheShatteredHourglass.getRandomItem() as Treasure

        gameField = arrayOf(null, null, null, null, null, null, null, null, null)
        fieldVisited = arrayOf(false, false, false, false, false, false, false, false, false)
        setRandomItemPosition(arcana)
        setRandomItemPosition(courier)
        setRandomItemPosition(treasure)
    }

    //TODO("Переделать генерацию(сделать без цикла, с рандомом на 9 чисел)")
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