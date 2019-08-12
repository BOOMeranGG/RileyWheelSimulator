package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.ItemsInTreasureRepository
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logErr
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.lang.RuntimeException
import java.util.*

class TreasureOpenerController(private val treasureItemsRepository: ItemsInTreasureRepository) {

    private var itemList = mutableListOf<InnerItem>()

    companion object {

        private val random = Random()

        fun getFastWinner(itemList: List<InnerItem>, deletedItems: List<InnerItem>): InnerItem {
            val items = itemList.toMutableList()
            for (deletedItem in deletedItems) {
                for (item in items) {
                    if (item.getName() == deletedItem.getName()) {
                        items.remove(item)
                        break
                    }
                }
            }

            for (i in 0 until items.size - 1) {
                val looser = chooseLooser(items[0], items[1])
                items.remove(looser)
            }
            return items.first()
        }

        private fun chooseLooser(first: InnerItem, second: InnerItem): InnerItem {
            val firstChance = first.priority
            val secondChance = second.priority

            val randNum = random.nextInt(firstChance + secondChance) + 1

            return if (randNum <= firstChance) {
                first
            } else {
                second
            }
        }
    }

    fun getLooserBetweenTwoItems(first: InnerItem, second: InnerItem): InnerItem {
        val looser = chooseLooser(first, second)

        itemList.remove(looser)
        return looser
    }

    fun getRandomItem(secondItem: InnerItem?): InnerItem {
        if (itemList.size == 1) {
            return secondItem as InnerItem
        }
        while (true) {
            val randItem = itemList[random.nextInt(itemList.size)]
            if (randItem != secondItem) {
                return randItem
            }
        }
    }

    fun createItemSet(treasureName: String): List<InnerItem> {
        itemList = treasureItemsRepository.getItemsFromTreasure(treasureName).toMutableList()
        itemList.sort()
        return itemList
    }

    //TODO("ИСПРАВИТЬ!")
    fun createItemSetWithoutExceptList(treasureName: String, exceptList: List<InnerItem>): List<InnerItem> {
        itemList = treasureItemsRepository.getItemsFromTreasure(treasureName).toMutableList()
        for (i in 0 until exceptList.size) {
            for (j in 0 until itemList.size) {
                if (exceptList[i].getName() == itemList[j].getName()) {
                    itemList.removeAt(j)
                    break
                }
            }
        }
        itemList.sort()
        return itemList
    }

    fun isThisItemAlive(item: InnerItem): Boolean = (itemList.find { it.getName() == item.getName() } != null)

    fun getWinnerItem(): InnerItem {
        return itemList.first()
    }

    fun isWin(): Boolean = itemList.size == 1
}