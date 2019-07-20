package com.orange_infinity.rileywheelsimulator.data_layer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.ItemsInTreasureRepository
import java.lang.RuntimeException

class InnerItemsRepositoryImpl private constructor(context: Context?) : ItemsInTreasureRepository {

    private var database = InventoryDataBaseOpenHelper(context).writableDatabase

    companion object {
        private var instance: InnerItemsRepositoryImpl? = null

        fun getInstance(context: Context?): InnerItemsRepositoryImpl {
            if (instance == null) {
                return InnerItemsRepositoryImpl(context)
            }
            return instance as InnerItemsRepositoryImpl
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

    fun getInnerItem(itemName: String): InnerItem {
        if (!isTableInitialize()) {
            initTable()
        }
        val cursor = queryItem(ItemsInTreasureDbSchema.Cols.ITEM_NAME + " = ?", arrayOf(itemName))
        cursor.use {
            cursor.moveToFirst()
            return createInnerItem(cursor)
        }
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
        insertSecondPart(tableName)
        insertThirdPart(tableName)
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

    private fun insertSecondPart(tableName: String) {
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "SpellDevourer", Rarity.RARE, Heroes.Rubick, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "DorsalDoom", Rarity.RARE, Heroes.Tidehunter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "LightedScepterOfSerendipity", Rarity.RARE, Heroes.OgreMagi, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "CrownOfOmoz", Rarity.RARE, Heroes.Doom, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "IcewrackPack", Rarity.RARE, Heroes.Lycan, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "DemonicCollar", Rarity.RARE, Heroes.Lion, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "DarkTracer", Rarity.RARE, Heroes.BountyHunter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "Mr.DepthDriller", Rarity.RARE, Heroes.Meepo, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "HookOfTheSorrowfulPrey", Rarity.MYTHICAL, Heroes.Pudge, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "HallowedHorde", Rarity.MYTHICAL, Heroes.NaturesProphet, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "Torchbearer", Rarity.MYTHICAL, Heroes.Warlock, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "HollowJack", Rarity.LEGENDARY, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GhastlyTreasureOfDiretide", "MaterializeItem", Rarity.UNCOMMON, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "FlyingDesolation", Rarity.RARE, Heroes.BountyHunter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "TwinSerpentBow", Rarity.MYTHICAL, Heroes.Medusa, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "RavenScythe", Rarity.RARE, Heroes.Necrophos, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "HeaddressOfTheProtector", Rarity.RARE, Heroes.SkywrathMage, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "Wavecutter", Rarity.RARE, Heroes.Slark, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "Evildoer", Rarity.RARE, Heroes.PhantomAssassin, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "AghanimsBasher", Rarity.RARE, Heroes.Brewmaster, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "NaturesGrip", Rarity.RARE, Heroes.NaturesProphet, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "MeatDragger", Rarity.RARE, Heroes.Pudge, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "StoneInfusion", Rarity.RARE, Heroes.Lion, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "Shadowshards", Rarity.RARE, Heroes.Riki, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "SpineSplitter", Rarity.MYTHICAL, Heroes.WraithKing, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "FearlessBadger", Rarity.MYTHICAL, Heroes.Any, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "MoroksMechanicalMediary", Rarity.MYTHICAL, Heroes.Any, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "TickledTegu", Rarity.MYTHICAL, Heroes.Any, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "QuirtsSummerStash", "BabyRoshan", Rarity.LEGENDARY, Heroes.Any, VERY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "IceCrystalBow", Rarity.UNCOMMON, Heroes.DrowRanger, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "WheelOfFortitude", Rarity.UNCOMMON, Heroes.Tidehunter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "Deathwielder", Rarity.RARE, Heroes.PhantomAssassin, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "HammerOfHolyWords", Rarity.RARE, Heroes.Omniknight, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "AncientMaskOfIntimidation", Rarity.RARE, Heroes.Juggernaut, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "DemonClaive", Rarity.RARE, Heroes.Sven, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "HammerTime", Rarity.RARE, Heroes.Tidehunter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "Timebreaker", Rarity.IMMORTAL, Heroes.FacelessVoid, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "ElementalIceInfusion", Rarity.RARE, Heroes.Tiny, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SithilsSummerStash", "DragonclawHook", Rarity.IMMORTAL, Heroes.FacelessVoid, VERY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "TroveCask", "LambToTheSlaughter", Rarity.IMMORTAL, Heroes.ShadowShaman, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "TroveCask", "Gravelmaw", Rarity.IMMORTAL, Heroes.EarthSpirit, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "TroveCask", "Empyrean", Rarity.IMMORTAL, Heroes.SkywrathMage, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "TroveCask", "SeveringCrest", Rarity.IMMORTAL, Heroes.Razor, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "TroveCask", "PaleMausoleum", Rarity.IMMORTAL, Heroes.Undying, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "TroveCask", "Doomling", Rarity.IMMORTAL, Heroes.Any, VERY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "SapphireCask", "Winterblight", Rarity.IMMORTAL, Heroes.WraithKing, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SapphireCask", "SkitteringDesolation", Rarity.IMMORTAL, Heroes.Weaver, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SapphireCask", "MoonGriffon", Rarity.IMMORTAL, Heroes.Mirana, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SapphireCask", "Serrakura", Rarity.IMMORTAL, Heroes.Juggernaut, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SapphireCask", "RapiersOfTheBurningGod", Rarity.IMMORTAL, Heroes.EmberSpirit, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "SapphireCask", "CladdishCudgel", Rarity.IMMORTAL, Heroes.Tidehunter, VERY_RARE, 0f
            )
        )
    }

    private fun insertThirdPart(tableName: String) {
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "EulsScepterOfTheMagus",
                Rarity.RARE,
                Heroes.Rubick,
                REGULAR,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "RigwarlsSpinyDemeanor",
                Rarity.RARE,
                Heroes.Bristleback,
                REGULAR,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass", "DefiledStinger", Rarity.RARE, Heroes.SandKing, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "TentaclesOfNetherReach",
                Rarity.RARE,
                Heroes.Pugna,
                REGULAR,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "HereticEnclave",
                Rarity.RARE,
                Heroes.DeathProphet,
                REGULAR,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "ThunderRoguesBraid",
                Rarity.RARE,
                Heroes.StormSpirit,
                REGULAR,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass", "ScrappersHelm", Rarity.RARE, Heroes.Clockwerk, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "FlailOfTheDrunkenCask",
                Rarity.RARE,
                Heroes.Brewmaster,
                REGULAR,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass",
                "AgedSpiritOfTide",
                Rarity.MYTHICAL,
                Heroes.Kunkka,
                VERY_RARE,
                0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "GenuineTreasureOfTheShatteredHourglass", "Snapjaw", Rarity.MYTHICAL, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "AdoringWingfall", Rarity.IMMORTAL, Heroes.Omniknight, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "CrimsonPique", Rarity.IMMORTAL, Heroes.Weaver, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "ControlledBurn", Rarity.IMMORTAL, Heroes.Timbersaw, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "VirgasArc", Rarity.IMMORTAL, Heroes.Enchantress, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "FortunesTout", Rarity.IMMORTAL, Heroes.Juggernaut, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "PhantomConcord", Rarity.IMMORTAL, Heroes.PhantomLancer, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "SolarForge", Rarity.IMMORTAL, Heroes.Phoenix, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "GoldenFortunesTout", Rarity.IMMORTAL, Heroes.Juggernaut, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureI2016", "MaceOfAeons", Rarity.IMMORTAL, Heroes.FacelessVoid, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "PeregrineFlight", Rarity.IMMORTAL, Heroes.Windranger, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "PaleAugur", Rarity.IMMORTAL, Heroes.Undying, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "ShadowMasquerade", Rarity.IMMORTAL, Heroes.Riki, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "CrownOfTears", Rarity.IMMORTAL, Heroes.Morphling, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "ShearingDeposition", Rarity.IMMORTAL, Heroes.Lich, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "BracersOfTheCavernLuminar", Rarity.IMMORTAL, Heroes.Earthshaker, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "PulsarRemnant", Rarity.IMMORTAL, Heroes.Mirana, RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "GoldenShadowMasquerade", Rarity.IMMORTAL, Heroes.Riki, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2016", "DarkArtistry", Rarity.IMMORTAL, Heroes.Invoker, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "TheBasherBlades", Rarity.IMMORTAL, Heroes.AntiMage, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "JewelOfAeons", Rarity.IMMORTAL, Heroes.FacelessVoid, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "ResistivePinfold", Rarity.IMMORTAL, Heroes.Disruptor, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "VigilSignet", Rarity.IMMORTAL, Heroes.Sven, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "TormentedStaff", Rarity.IMMORTAL, Heroes.Leshrac, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "Rollermawster", Rarity.IMMORTAL, Heroes.Tinker, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "ArmsOfDesolation", Rarity.IMMORTAL, Heroes.ShadowFiend, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureII2015", "GoldenBasherBlades", Rarity.IMMORTAL, Heroes.AntiMage, EXTREMELY_RARE, 0f
            )
        )
        // -------------------------------------------------------------------------------------------------------------
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "PistonImpaler", Rarity.IMMORTAL, Heroes.Bristleback, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "AtomicRayThrusters", Rarity.IMMORTAL, Heroes.Gyrocopter, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "BonkersTheMad", Rarity.IMMORTAL, Heroes.WitchDoctor, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "MagusApex", Rarity.IMMORTAL, Heroes.Invoker, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "SufferwoodSapling", Rarity.IMMORTAL, Heroes.NaturesProphet, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "ColossalCrystalChorus", Rarity.IMMORTAL, Heroes.Meepo, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "FocalResonance", Rarity.IMMORTAL, Heroes.TemplarAssassin, REGULAR, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "RighteousThunderbolt", Rarity.IMMORTAL, Heroes.Zeus, VERY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "GoldenAtomicRayThrusters", Rarity.IMMORTAL, Heroes.Gyrocopter, EXTREMELY_RARE, 0f
            )
        )
        database.insert(
            tableName, null, getContentValue(
                "ImmortalTreasureIII2015", "AlmondTheFrondillo", Rarity.IMMORTAL, Heroes.Any, EXTREMELY_RARE, 0f
            )
        )
    }

    private fun insertFourth() {
    }
}