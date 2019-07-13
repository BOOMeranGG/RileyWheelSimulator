package com.orange_infinity.rileywheelsimulator.entities_layer.items

enum class Commentator(val rarity: Rarity = Rarity.MYTHICAL): Item {
    ;

    override fun getRandomItem(): Item {
        TODO("not implemented")
    }

    override fun getName() = this.name
}