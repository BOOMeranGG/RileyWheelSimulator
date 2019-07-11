package com.orange_infinity.rileywheelsimulator.data_layer.db

class InventoryDbSchema {

    object Cols {
        const val NAME = "enumName"
        const val COUNT = "count"
        const val ITEM_TYPE = "itemType"
    }

    object ItemTable {
        const val NAME = "item"
    }

    object TreasureTable {
        const val NAME = "treasure"
    }
}