package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.ItemsInTreasureRepository
import java.util.*

class TreasureOpener(private val treasureItemsRepository: ItemsInTreasureRepository) {

    private var itemList = mutableListOf<InnerItem>()
    private val random = Random()

    fun getItemSet(treasureName: String): List<InnerItem> {
        itemList = treasureItemsRepository.getItemsFromTreasure(treasureName).toMutableList()
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