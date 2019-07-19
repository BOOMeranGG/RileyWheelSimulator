package com.orange_infinity.rileywheelsimulator.data_layer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.ItemsInTreasureRepository
import java.lang.RuntimeException

class ItemsInTreasureRepositoryImpl private constructor(context: Context?) : ItemsInTreasureRepository {

    private var database = InventoryDataBaseOpenHelper(context).writableDatabase

    companion object {
        private var instance: ItemsInTreasureRepositoryImpl? = null

        fun getInstance(context: Context?): ItemsInTreasureRepositoryImpl {
            if (instance == null) {
                return ItemsInTreasureRepositoryImpl(context)
            }
            return instance as ItemsInTreasureRepositoryImpl
        }
    }

    override fun getItemsFromTreasure(treasureName: String): Array<InnerItem> {
        if (!isTableInitialize()) {
            initTable()
        }
        val cursor = queryItem(ItemsInTreasureDbSchema.Cols.TREASURE_NAME + " = ?", arrayOf(treasureName))
        val itemsInTreasure = mutableListOf<InnerItem>()
        cursor.use {
            cursor.moveToFirst()
            if (cursor.isAfterLast) {
                throw RuntimeException("This treasure wasn't add in DataBase")
            }
            while (!cursor.isAfterLast) {
                itemsInTreasure.add(createInnerItem(cursor))
                cursor.moveToNext()
            }
        }
        return itemsInTreasure.toTypedArray()
    }

    private fun isTableInitialize(): Boolean {
        val cursor = queryItem(null, null)
        cursor.use {
            cursor.moveToFirst()
            return !cursor.isAfterLast
        }
    }

    private fun createInnerItem(cursor: Cursor): InnerItem {
        val treasureName = cursor.getString(cursor.getColumnIndex(ItemsInTreasureDbSchema.Cols.TREASURE_NAME))
        val itemName = cursor.getString(cursor.getColumnIndex(ItemsInTreasureDbSchema.Cols.ITEM_NAME))
        val rarity = cursor.getString(cursor.getColumnIndex(ItemsInTreasureDbSchema.Cols.RARITY))
        val hero = cursor.getString(cursor.getColumnIndex(ItemsInTreasureDbSchema.Cols.HERO))
        val priority = cursor.getInt(cursor.getColumnIndex(ItemsInTreasureDbSchema.Cols.PRIORITY))
        val cost = cursor.getFloat(cursor.getColumnIndex(ItemsInTreasureDbSchema.Cols.COST))

        return InnerItem(treasureName, itemName, Rarity.valueOf(rarity), Heroes.valueOf(hero), priority, cost)
    }

    private fun getContentValue(
        treasureName: String, itemName: String, rarity: Rarity, hero: Heroes, priority: Int, cost: Float
    ): ContentValues {
        val values = ContentValues()
        values.put(ItemsInTreasureDbSchema.Cols.TREASURE_NAME, treasureName)
        values.put(ItemsInTreasureDbSchema.Cols.ITEM_NAME, itemName)
        values.put(ItemsInTreasureDbSchema.Cols.RARITY, rarity.name)
        values.put(ItemsInTreasureDbSchema.Cols.HERO, hero.name)
        values.put(ItemsInTreasureDbSchema.Cols.PRIORITY, priority)
        values.put(ItemsInTreasureDbSchema.Cols.COST, cost)

        return values
    }

    private fun queryItem(whereClause: String?, whereArgs: Array<String>?): Cursor {
        return database.query(
            ItemsInTreasureDbSchema.ItemsInTreasureTable.NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null
        )
    }

    private fun initTable() {
        val tableName = ItemsInTreasureDbSchema.ItemsInTreasureTable.NAME
        insertFirstPart(tableName)
    }

    private fun insertFirstPart(tableName: String) {
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "KunkkasShadowBlade", Rarity.MYTHICAL, Heroes.Kunkka, EXTREMELY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "HeavenPiercingPauldrons", Rarity.MYTHICAL, Heroes.Invoker, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "FrostOwlsBeacon", Rarity.MYTHICAL, Heroes.CrystalMaiden, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "PrisonersAnchor", Rarity.MYTHICAL, Heroes.Alchemist, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "NyxAssassinsDagon", Rarity.MYTHICAL, Heroes.NyxAssasin, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "CrownOfTheDeathPriestess", Rarity.MYTHICAL, Heroes.DeathProphet, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "CrestOfTheWyrmLords", Rarity.MYTHICAL, Heroes.DragonKnight, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "ChaosKnightsArmletOfMordiggian", Rarity.MYTHICAL, Heroes.ChaosKnight, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckbox", "FormOfTheOnyxGrove", Rarity.MYTHICAL, Heroes.LoneDruid, REGULAR, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "ManiasMask", Rarity.IMMORTAL, Heroes.DrowRanger, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "InverseBayonet", Rarity.IMMORTAL, Heroes.Kunkka, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "WhiteSentry", Rarity.IMMORTAL, Heroes.CrystalMaiden, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "ElixirOfDragonsBreath", Rarity.IMMORTAL, Heroes.Brewmaster, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "CrystalDryad", Rarity.IMMORTAL, Heroes.Tiny, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "BladeOfTears", Rarity.IMMORTAL, Heroes.Morphling, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "FlourishingLodestar", Rarity.IMMORTAL, Heroes.Enchantress, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "SwiftClaw", Rarity.IMMORTAL, Heroes.Ursa, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "SoulDiffuser", Rarity.IMMORTAL, Heroes.Spectre, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "SplatteringForcipule", Rarity.IMMORTAL, Heroes.Venomancer, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "GeodesicEidolon", Rarity.IMMORTAL, Heroes.Enigma, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "Frull", Rarity.MYTHICAL, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "MechjawTheBoxhound", Rarity.MYTHICAL, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase", "Oculopus", Rarity.MYTHICAL, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2016", "HuntersHoard", Rarity.IMMORTAL, Heroes.BountyHunter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2016", "MulctantPall", Rarity.IMMORTAL, Heroes.Lion, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2016", "DamarakanMuzzle", Rarity.IMMORTAL, Heroes.Silencer, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2016", "BlisteringShade", Rarity.IMMORTAL, Heroes.WraithKing, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2016", "Krobeling", Rarity.IMMORTAL, Heroes.Any, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2016", "ImbuedGoldenTroveCarafe2016", Rarity.IMMORTAL, Heroes.Any, VERY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2015", "ShatterblastCrown", Rarity.IMMORTAL, Heroes.AncientApparition, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2015", "ThirstOfEztzhok", Rarity.IMMORTAL, Heroes.Bloodseeker, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2015", "RazzilsMidasKnuckles", Rarity.IMMORTAL, Heroes.Alchemist, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2015", "ParaflareCannon", Rarity.IMMORTAL, Heroes.Clockwerk, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2015", "TollingShadows", Rarity.IMMORTAL, Heroes.Visage, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "LocklessLuckvase2015", "Venoling", Rarity.IMMORTAL, Heroes.Any, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GoldenTroveCarafe2015", "TollingShadows", Rarity.IMMORTAL, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "BlessedLuckvessel", "MoltenClaw", Rarity.IMMORTAL, Heroes.Axe, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "BlessedLuckvessel", "FlutteringStaff", Rarity.IMMORTAL, Heroes.NaturesProphet, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "BlessedLuckvessel", "BloodfeatherWings", Rarity.IMMORTAL, Heroes.QueenOfPain, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "BlessedLuckvessel", "HellsUsher", Rarity.IMMORTAL, Heroes.PhantomAssassin, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "BlessedLuckvessel", "TheBarbOfSkadi", Rarity.IMMORTAL, Heroes.Slark, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "BlessedLuckvessel", "TheGoldenBarbOfSkadi", Rarity.IMMORTAL, Heroes.Slark, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------

    }
}