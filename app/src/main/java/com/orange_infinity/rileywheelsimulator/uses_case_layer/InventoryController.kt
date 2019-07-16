package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class InventoryController(private val inventoryRepository: InventoryRepository) {

    fun getItemsFromInventory(): List<ItemBox> {
        val items = inventoryRepository.getItemsFromInventory()
        val itemsBox = items.map { ItemBox(it.key, it.value) }

        return sortItems(itemsBox)
    }

    fun getTreasuresFromInventory(): List<ItemBox> {
        val items = inventoryRepository.getTreasuresFromInventory()
        val itemsBox = items.map { ItemBox(it.key, it.value) }

        return sortItems(itemsBox)
    }

    fun addItem(item: Item) {
        inventoryRepository.saveRileyItem(item)
        logInf(MAIN_LOGGER_TAG, "Item \"${item.getItemName()}\" added")
    }

    fun deleteItem(item: Item) {
        inventoryRepository.deleteItem(item)
        logInf(MAIN_LOGGER_TAG, "Item \"${item.getItemName()}\" was deleted")
    }

    private fun sortItems(itemsBox: List<ItemBox>): List<ItemBox> {
        val iBox = itemsBox.toMutableList()
        iBox.sort()
        return iBox
    }
}