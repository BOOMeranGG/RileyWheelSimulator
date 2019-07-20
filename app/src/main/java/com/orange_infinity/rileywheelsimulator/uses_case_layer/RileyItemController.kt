package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository

class RileyItemController(private val inventoryRepository: InventoryRepository) {

    private val arcanaChance = 0.2
    private val courierChance = 0.4
    private val commentatorChance = 0.6
    private val treasureChance = 1

    fun addRandomItem(): Item {
        val rand = Math.random()
        val item = when {
            rand < arcanaChance -> Arcana.BladesOfVothDomosh.getRandomItem()
            rand < courierChance -> Courier.AlphidOfLecaciida.getRandomItem()
            rand < commentatorChance -> Commentator.AnnouncerAxe.getRandomItem()
            else -> {
                Treasure.BlessedLuckvessel.getRandomItem()
            }
        }
        inventoryRepository.saveRileyItem(item)
        return item
    }
}