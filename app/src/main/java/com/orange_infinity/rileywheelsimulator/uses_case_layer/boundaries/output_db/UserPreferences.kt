package com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db

import android.content.Context

interface UserPreferences {

    fun saveTotalItemCost(context: Context?, cost: Int, add: Int)

    fun saveStringInPreferences(context: Context?, info: String, tag: String)

    fun saveIntInPreferences(context: Context?, info: Int, tag: String)

    fun getStringFromPreferences(context: Context?, tag: String): String?

    fun getIntFromPreferences(context: Context?, tag: String): Int?
}