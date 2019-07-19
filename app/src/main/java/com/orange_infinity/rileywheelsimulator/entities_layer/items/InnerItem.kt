package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.util.convertFromCamelCase

class InnerItem(
    val treasureName: String,
    private val name: String,
    val rarity: Rarity,
    val hero: Heroes,
    val priority: Int,
    val cost: Float
) : Item {

    override fun getRandomItem(): Item {
        return this
    }

    override fun getName(): String = this.name

    override fun getItemName(): String = convertFromCamelCase(this.name)

    override fun getCost(): Int = cost.toInt()

    override fun getRarity(): String = rarity.toString()
}