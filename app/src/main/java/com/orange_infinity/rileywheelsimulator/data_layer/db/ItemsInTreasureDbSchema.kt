package com.orange_infinity.rileywheelsimulator.data_layer.db

class ItemsInTreasureDbSchema {

    object Cols {
        const val TREASURE_NAME = "treasureName"
        const val ITEM_NAME = "itemName"
        const val RARITY = "rarity"
        const val HERO = "hero"
        const val PRIORITY = "priority"
        const val COST = "cost"
    }

    object ItemsInTreasureTable {
        const val NAME = "itemsInTreasure"
    }
}