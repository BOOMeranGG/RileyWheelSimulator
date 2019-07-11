package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

enum class Treasure(private val itemName: String, private val cost: Int): Item {
    ImmortalStrongbox("Immortal Strongbox", 5),
    HerosHeirloom("Hero's Heirloom", 2),
    GoldenTroveCask("Golden Trove Cask", 11),
    FlutteringCache("Fluttering Cache", 12),
    FlutteringBonusCache("Fluttering Bonus Cache", 7),
    ChampionsChest("Champion's Chest", 10),
    BlessedLuckvessel("Blessed Luckvessel", 23),
    TreasureOfMoltenSteel("Treasure of Molten Steel", 9),
    TroveCarafe("Trove Carafe", 4),
    TreasureOfTheOnyxEye("Treasure of the Onyx Eye", 8);

    //    private val arcanaValues: Array<Arcana> = values()
    private val random = Random()

    override fun getRandomItem(): Item {
        val treasureValues: Array<Treasure> = values()
        val randNum = random.nextInt(treasureValues.size)
        logInf(MAIN_LOGGER_TAG, "Arcana getting random: ${treasureValues[randNum].itemName}")
        return treasureValues[randNum]
    }

    override fun getName() = this.name

    override fun getItemName(): String = itemName

    override fun getCost(): Int = cost
}