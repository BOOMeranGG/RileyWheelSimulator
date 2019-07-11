package com.orange_infinity.rileywheelsimulator.entities_layer.items

interface Item {

    fun getRandomItem(): Item

    fun getName(): String

    fun getItemName(): String

    fun getCost(): Int
}