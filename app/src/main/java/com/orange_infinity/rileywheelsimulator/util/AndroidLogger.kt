package com.orange_infinity.rileywheelsimulator.util

import android.util.Log
import com.orange_infinity.rileywheelsimulator.BuildConfig

const val MAIN_LOGGER_TAG : String = "MainLoggerTag"
const val DB_LOGGER_TAG : String = "DataBaseLoggerTag"
const val CASINO_LOGGER_TAG : String = "CasinoLoggerTag"

fun logInf(tag: String, info: String) {
    if (BuildConfig.DEBUG) {
        Log.i(tag, info)
    }
}

fun logDeb(tag: String, info: String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, info)
    }
}

fun logErr(tag: String, info: String, e: Exception) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, info, e)
    }
}

fun logWar(tag: String, info: String) {
    if (BuildConfig.DEBUG) {
        Log.w(tag, info)
    }
}

fun logVer(tag: String, info: String) {
    if (BuildConfig.DEBUG) {
        Log.v(tag, info)
    }
}

fun logWtf(tag: String, info: String) {
    if (BuildConfig.DEBUG) {
        Log.wtf(tag, info)
    }
}