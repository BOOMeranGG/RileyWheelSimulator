package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.ItemsInTreasureRepository

class TreasureOpener(private val treasureItemsRepository: ItemsInTreasureRepository) {

    private var itemList =  arrayOf<InnerItem>()

    fun getItemSet(treasureName: String): Array<InnerItem> {
        itemList = treasureItemsRepository.getItemsFromTreasure(treasureName)
        return itemList
    }

    fun getWinnerItem(): Item? {
        return null
    }
}