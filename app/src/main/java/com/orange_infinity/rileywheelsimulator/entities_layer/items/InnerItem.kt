package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.util.convertFromCamelCase

class InnerItem(
    val treasureName: String = "",
    private val name: String,
    val rarity: Rarity = Rarity.COMMON,
    val hero: Heroes = Heroes.Any,
    val priority: Int = 0,
    private val cost: Float = 0.0f
) : Item, Comparable<InnerItem> {

    override fun compareTo(other: InnerItem): Int {
        val pr = this.priority - other.priority
        if (pr != 0) {
            return pr
        }
        return (this.cost - other.cost).toInt()
    }

    override fun getRandomItem(): Item {
        return this
    }

    override fun getName(): String = this.name

    override fun getItemName(): String = convertFromCamelCase(this.name)

    override fun getCost(): Float = cost

    override fun getRarity(): String = rarity.toString()
}