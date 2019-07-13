package com.orange_infinity.rileywheelsimulator.entities_layer.items

enum class SetItem(rarity: Rarity): Item {
    ;

    override fun getRandomItem(): Item {
        TODO("not implemented")
    }

    override fun getName() = this.name
}