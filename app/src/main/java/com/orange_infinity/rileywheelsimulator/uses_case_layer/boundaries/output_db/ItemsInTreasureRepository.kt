package com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db

import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem

interface ItemsInTreasureRepository {

    fun getItemsFromTreasure(treasureName: String): Array<InnerItem>
}