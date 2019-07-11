package com.orange_infinity.rileywheelsimulator.data_layer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db.InventoryRepository
import com.orange_infinity.rileywheelsimulator.util.DB_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import com.orange_infinity.rileywheelsimulator.util.toImmutableMap
import java.lang.RuntimeException

class InventoryRepositoryImpl(context: Context?) : InventoryRepository {

    private var database = InventoryDataBaseOpenHelper(context).writableDatabase

    companion object {
        private var instance: InventoryRepositoryImpl? = null

        fun getInstance(context: Context?): InventoryRepositoryImpl {
            if (instance == null)
                return InventoryRepositoryImpl(context)
            return instance as InventoryRepositoryImpl
        }
    }

    override fun saveRileyItem(item: Item) {
        val tableName: String = if (item is Treasure) {
            InventoryDbSchema.TreasureTable.NAME
        } else {
            InventoryDbSchema.ItemTable.NAME
        }
        val itemType = getItemType(item)
        val count = getCurrentCountOfItem(item, tableName) + 1
        val contentValues = getContentValue(item, count, itemType)

        if (count == 1) {
            database.insert(tableName, null, contentValues)
            logInf(DB_LOGGER_TAG, "\"$item\" was INSERT in db, type = $itemType, count = $count")
        } else {
            database.update(tableName, contentValues, "${InventoryDbSchema.Cols.NAME} = ?", arrayOf(item.getName()))
            logInf(DB_LOGGER_TAG, "\"$item\" was UPDATE in db, type = $itemType, count = $count")
        }
    }

    //TODO("Стоит сразу завернуть в объект ItemBox")
    override fun getItemsFromInventory(): Map<Item, Int> {
        val items = mutableMapOf<Item, Int>()
        val cursor = queryItem(InventoryDbSchema.ItemTable.NAME, null, null)
        cursor.use {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val count = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.COUNT)).toInt()
                items[getItemFromString(
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.NAME)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.ITEM_TYPE))
                )] = count
                cursor.moveToNext()
            }
        }
        return items.toImmutableMap()
    }

    override fun getTreasuresFromInventory(): Map<Item, Int> {
        val items = mutableMapOf<Item, Int>()
        val cursor = queryItem(InventoryDbSchema.TreasureTable.NAME, null, null)
        cursor.use {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val count = cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.COUNT)).toInt()
                items[getItemFromString(
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.NAME)),
                    cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.ITEM_TYPE))
                )] = count
                cursor.moveToNext()
            }
        }
        return items.toImmutableMap()
    }

    override fun deleteItem(item: Item): Boolean {
        val tableName: String = if (item is Treasure) {
            InventoryDbSchema.TreasureTable.NAME
        } else {
            InventoryDbSchema.ItemTable.NAME
        }
        val count = getCurrentCountOfItem(item, tableName)

        return when {
            (count == 1) -> {
                database.delete(tableName, "${InventoryDbSchema.Cols.NAME} = ?", arrayOf(item.getName()))
                logInf(DB_LOGGER_TAG, "\"$item\" was DELETE in db, count = $count")
                true
            }
            (count > 1) -> {
                val itemType = getItemType(item)
                val contentValues = getContentValue(item, count, itemType)
                database.update(tableName, contentValues, "${InventoryDbSchema.Cols.NAME} = ?", arrayOf(item.getName()))
                logInf(DB_LOGGER_TAG, "\"$item\" was UPDATE in db, type = $itemType, count = $count")
                true
            }
            else -> false
        }
    }

    private fun getContentValue(item: Item, count: Int, itemType: String): ContentValues {
        val values = ContentValues()
        values.put(InventoryDbSchema.Cols.NAME, item.getName())
        values.put(InventoryDbSchema.Cols.COUNT, count)
        values.put(InventoryDbSchema.Cols.ITEM_TYPE, itemType)

        return values
    }

    private fun getCurrentCountOfItem(item: Item, tableName: String): Int {
        val cursor = queryItem(tableName, InventoryDbSchema.Cols.NAME + " = ?", arrayOf(item.getName()))
        cursor.use {
            cursor.moveToFirst()
            if (cursor.isAfterLast) {
                return 0
            }
            return cursor.getString(cursor.getColumnIndex(InventoryDbSchema.Cols.COUNT)).toInt()
        }
    }

    private fun getItemType(item: Item): String {
        when (item) {
            is Arcana -> return ARCANA
            is Commentator -> return COMMENTATOR
            is Courier -> return COURIER
            is SetItem -> return SET_ITEM
            is Treasure -> return TREASURE
        }
        throw RuntimeException()
    }

    private fun queryItem(tableName: String, whereClause: String?, whereArgs: Array<String>?): Cursor {
        return database.query(
            tableName,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null
        )
    }

    private fun getItemFromString(itemName: String, itemType: String): Item {
        return when (itemType) {
            ARCANA -> Arcana.valueOf(itemName)
            COMMENTATOR -> Commentator.valueOf(itemName)
            COURIER -> Courier.valueOf(itemName)
            SET_ITEM -> SetItem.valueOf(itemName)
            TREASURE -> Treasure.valueOf(itemName)
            else -> throw RuntimeException()
        }
    }
}
