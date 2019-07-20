package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.ItemsInTreasureRepository
import java.util.*

class TreasureOpenerController(private val treasureItemsRepository: ItemsInTreasureRepository) {

    private var itemList = mutableListOf<InnerItem>()
    private val random = Random()

    fun createItemSet(treasureName: String): List<InnerItem> {
        itemList = treasureItemsRepository.getItemsFromTreasure(treasureName).toMutableList()
        itemList.sort()
        return itemList
    }

        //TODO("ИСПРАВИТЬ!")
    fun createItemSetExceptList(treasureName: String, exceptList: List<InnerItem>): List<InnerItem> {
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

    fun gerRandomItem(secondItem: InnerItem?): InnerItem {
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

    fun getLooserBetweenDoubleItems(first: InnerItem, second: InnerItem): InnerItem {
        val randNum = (Math.random() * 2).toInt()
        val looser = if (randNum == 0) {
            first
        } else {
            second
        }
        itemList.remove(looser)
        return looser
    }

    fun isThisItemAlive(item: InnerItem): Boolean = (itemList.find { it.getName() == item.getName() } != null)

    fun getWinnerItem(): InnerItem {
        return itemList.first()
    }

    fun isWin(): Boolean = itemList.size == 1
}