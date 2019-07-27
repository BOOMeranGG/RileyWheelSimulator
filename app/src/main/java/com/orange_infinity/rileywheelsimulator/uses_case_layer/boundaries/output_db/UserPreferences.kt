package com.orange_infinity.rileywheelsimulator.uses_case_layer.boundaries.output_db

import android.content.Context

interface UserPreferences {

    fun saveStringInPreferences(context: Context?, info: String, tag: String)

    fun saveIntInPreferences(context: Context?, info: Int, tag: String)

    fun getStringFromPreferences(context: Context?, tag: String): String?

    fun getIntFromPreferences(context: Context?, tag: String): Int?

    fun saveFloatInPreferences(context: Context?, info: Float, tag: String)

    fun getFloatFromPreferences(context: Context?, tag: String): Float?
}