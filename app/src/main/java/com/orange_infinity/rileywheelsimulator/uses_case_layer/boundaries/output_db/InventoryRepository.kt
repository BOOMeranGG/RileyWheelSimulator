package com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db

import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item

interface InventoryRepository {

    fun saveRileyItem(item: Item)

    fun deleteItem(item: Item): Boolean

    fun getItemsFromInventory(): Map<Item, Int>

    fun getTreasuresFromInventory(): Map<Item, Int>
}