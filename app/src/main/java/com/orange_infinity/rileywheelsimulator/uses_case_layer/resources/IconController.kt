package com.orange_infinity.rileywheelsimulator.uses_case_layer.resources

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import com.orange_infinity.rileywheelsimulator.entities_layer.Grade
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.convertFromCamelCase
import com.orange_infinity.rileywheelsimulator.util.logErr
import java.io.IOException
import java.io.InputStream

private const val ICON_FOLDER = "icons"
private const val ARCANA_FOLDER = "/arcana"
private const val COURIER_FOLDER = "/courier"
private const val COMMENTATOR_FOLDER = "/commentator"
private const val TREASURE_FOLDER = "/treasure"
private const val INNER_ITEMS = "/inner_items"
private const val GRADE = "/grade"

class IconController private constructor(context: Context?) {

    private val assets: AssetManager = context!!.assets

    companion object {
        private var instance: IconController? = null

        fun getInstance(context: Context?): IconController {
            if (instance == null) {
                return IconController(context)
                    .also { instance = it }
            }
            return instance as IconController
        }
    }

    fun getItemIconDrawable(item: Item): Drawable? {
        val prefix = when (item) {
            is Arcana -> ARCANA_FOLDER
            is Courier -> COURIER_FOLDER
            is Commentator -> COMMENTATOR_FOLDER
            is Treasure -> TREASURE_FOLDER
            is InnerItem -> "$INNER_ITEMS/${item.treasureName}"
            else -> return null
        }
        return getDrawableFromAsset(prefix, item.getName())
    }

    fun getGradeDrawable(grade: Grade): Drawable? {
        return getDrawableFromAsset(GRADE, grade.name.toLowerCase())
    }

    private fun getDrawableFromAsset(partFolder: String, fileName: String): Drawable? {
        var inputStream: InputStream? = null

        try {
            val assetPath = "${ICON_FOLDER + partFolder}/$fileName.png"
            inputStream = assets.open(assetPath)
            return Drawable.createFromStream(inputStream, null)
        } catch (e: IOException) {
            logErr(MAIN_LOGGER_TAG, "Could not load icon $fileName", e)
        } finally {
            inputStream?.close()
        }
        return null
    }
}