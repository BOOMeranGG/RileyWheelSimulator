package com.orange_infinity.rileywheelsimulator.uses_case_layer.resources

import android.content.Context
import android.content.res.AssetManager
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logErr
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


private const val FOLDER = "text_files"
private const val BASE_FILE_NAME = "nicknames_"
private const val FILE_EXTENSION = ".txt"

class NicknameCreator(val context: Context?) {

    private val assets: AssetManager = context!!.assets

    fun createSingleNickname(): String {
        val fileName = "$FOLDER/${BASE_FILE_NAME}1$FILE_EXTENSION"
        try {
            if (context == null)
                return "Gilfoil"
            val inputStream = assets.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream)
            val reader = BufferedReader(inputStreamReader)
            var str = reader.readLine()
            var result = ""

            var i = 0
            val random = Random()
            while (str != null) {
                i++
                if (random.nextInt(i) == 0)
                    result = str
                str = reader.readLine()
            }
            return result
        } catch (e: IOException) {
            logErr(MAIN_LOGGER_TAG, "Can not load text file($fileName) to create single bot name", e)
        }
        return "Gilfoil"
    }

    fun createNicknames(count: Int): List<String> {
        return listOf()
    }
}