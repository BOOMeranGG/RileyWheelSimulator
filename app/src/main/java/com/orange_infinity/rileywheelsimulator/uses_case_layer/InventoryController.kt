package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository

class InventoryController(private val inventoryRepository: InventoryRepository) {

    fun getItemsFromInventory(): List<ItemBox> {
        val items = inventoryRepository.getItemsFromInventory()
        val itemsBox = items.map { ItemBox(it.key, it.value) }

        //TODO("Отсортировать по цене, больше ---> меньше")
        return itemsBox
    }

    fun getTreasuresFromInventory(): List<ItemBox> {
        val items = inventoryRepository.getTreasuresFromInventory()
        val itemsBox = items.map { ItemBox(it.key, it.value) }

        //TODO("Отсортировать по цене, больше ---> меньше")
        return itemsBox
    }
}