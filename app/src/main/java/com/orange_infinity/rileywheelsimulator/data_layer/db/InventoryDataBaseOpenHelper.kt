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
        db.execSQL(getSqlToCreateTable(InventoryDbSchema.ItemTable.NAME))
        db.execSQL(getSqlToCreateTable(InventoryDbSchema.TreasureTable.NAME))
        logInf(DB_LOGGER_TAG, "DataBases was created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getSqlToCreateTable(tableName: String): String =
        "create table $tableName ( _id integer primary key autoincrement, " +
                "${InventoryDbSchema.Cols.NAME}, ${InventoryDbSchema.Cols.COUNT}, ${InventoryDbSchema.Cols.ITEM_TYPE})"
}
