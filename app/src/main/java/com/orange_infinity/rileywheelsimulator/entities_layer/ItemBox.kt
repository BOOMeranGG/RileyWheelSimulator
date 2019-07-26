package com.orange_infinity.rileywheelsimulator.entities_layer

import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item

data class ItemBox(val item: Item, var count: Int) : Comparable<ItemBox> {

    override fun compareTo(other: ItemBox): Int = (other.item.getCost() - item.getCost()).toInt()
}
