package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.Arcana
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Courier
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Treasure
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class RileyController(private val inventoryRepository: InventoryRepository) {

    private val arcanaChance = 0.25
    private val courierChance = 0.5
    private val treasureChance = 1

    fun addRandomItem(): Item {
        val rand = Math.random()
        val item = when {
            rand < arcanaChance -> Arcana.BladesOfVothDomosh.getRandomItem()
            rand < courierChance -> Courier.AlphidOfLecaciida.getRandomItem()
            else -> {
                Treasure.BlessedLuckvessel.getRandomItem()
            }
        }
        inventoryRepository.saveRileyItem(item)

        return item
    }

    fun sellItem(item: Item) {
        inventoryRepository.deleteItem(item)
        //SMTH else
    }
}