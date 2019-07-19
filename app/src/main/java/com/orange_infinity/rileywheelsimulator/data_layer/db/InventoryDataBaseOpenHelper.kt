package com.orange_infinity.rileywheelsimulator.data_layer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.orange_infinity.rileywheelsimulator.util.DB_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class InventoryDataBaseOpenHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        private const val DATABASE_NAME = "inventory.db"
        private const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(getSqlToCreateInventoryTable(InventoryDbSchema.ItemTable.NAME))
        db.execSQL(getSqlToCreateInventoryTable(InventoryDbSchema.TreasureTable.NAME))
        db.execSQL(getSqlToCreateInnerTreasureTable(ItemsInTreasureDbSchema.ItemsInTreasureTable.NAME))
        logInf(DB_LOGGER_TAG, "DataBases was created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    private fun getSqlToCreateInventoryTable(tableName: String): String =
        "create table $tableName ( _id integer primary key autoincrement, " +
                "${InventoryDbSchema.Cols.NAME}, ${InventoryDbSchema.Cols.COUNT}, ${InventoryDbSchema.Cols.ITEM_TYPE})"

    private fun getSqlToCreateInnerTreasureTable(tableName: String): String =
            "create table $tableName (_id integer primary key autoincrement, " +
                    "${ItemsInTreasureDbSchema.Cols.TREASURE_NAME}, ${ItemsInTreasureDbSchema.Cols.ITEM_NAME}, " +
                    "${ItemsInTreasureDbSchema.Cols.RARITY}, ${ItemsInTreasureDbSchema.Cols.HERO}, " +
                    "${ItemsInTreasureDbSchema.Cols.PRIORITY}, ${ItemsInTreasureDbSchema.Cols.COST})"
}
