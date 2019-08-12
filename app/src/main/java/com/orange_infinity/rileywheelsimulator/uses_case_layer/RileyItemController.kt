package com.orange_infinity.rileywheelsimulator.uses_case_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logWtf

class RileyItemController(private val inventoryRepository: InventoryRepository) {

    private val setChance = 50
    private val treasureChance = 80
    private val courierChance = 90
    private val commentatorChance = 98
    private val arcanaChance = 100

    fun addRandomItem(): Item {
        val rand = Math.random() * 100
        val item = when {
            rand < setChance -> SetItem.AcidHydra.getRandomItem()
            rand < treasureChance -> Treasure.GenuineTreasureOfTheShatteredHourglass.getRandomItem()
            rand < courierChance -> Courier.AlphidOfLecaciida.getRandomItem()
            rand < commentatorChance -> Commentator.AnnouncerAxe.getRandomItem()
            rand < arcanaChance -> Arcana.BladesOfVothDomosh.getRandomItem()
            else -> {
                logWtf(MAIN_LOGGER_TAG, "RileyItemController BUG!")
                Treasure.BlessedLuckvessel.getRandomItem()
            }
        }
        inventoryRepository.saveRileyItem(item)
        if (item is Treasure) {
            inventoryRepository.saveRileyItem(item)
        }
        return item
    }
}