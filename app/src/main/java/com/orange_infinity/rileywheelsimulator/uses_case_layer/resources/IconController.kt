package com.orange_infinity.rileywheelsimulator.uses_case_layer.resources

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.orange_infinity.rileywheelsimulator.entities_layer.Grade
import com.orange_infinity.rileywheelsimulator.entities_layer.items.*
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logErr
import java.io.IOException
import java.io.InputStream

private const val ICON_FOLDER = "icons"
private const val ARCANA_FOLDER = "/arcana"
private const val COURIER_FOLDER = "/courier"
private const val COMMENTATOR_FOLDER = "/commentator"
private const val TREASURE_FOLDER = "/treasure"
private const val INNER_ITEMS_FOLDER = "/inner_items"
private const val SET_ITEMS_FOLDER = "/set_items"
private const val GRADE = "/grade"
private const val FRAME_SIZE_MULTIPLIER = 1.1

class IconController private constructor(context: Context?) {

    private val assets: AssetManager = context!!.assets
    private val resources = context!!.resources

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
        val prefix = createPrefix(item)
        return getDrawableFromAsset(prefix, item.getName())
    }

    fun getItemIconDrawableWithBox(item: Item): Drawable? {
        val prefix = createPrefix(item)
        val iconDrawable = getDrawableFromAsset(prefix, item.getName()) ?: return null
        return setBoxToDrawable(iconDrawable, item)
    }

    fun getGradeDrawable(grade: Grade): Drawable? {
        return getDrawableFromAsset(GRADE, grade.name.toLowerCase())
    }

    private fun createPrefix(item: Item): String {
        return when (item) {
            is Arcana -> ARCANA_FOLDER
            is Courier -> COURIER_FOLDER
            is Commentator -> COMMENTATOR_FOLDER
            is Treasure -> TREASURE_FOLDER
            is SetItem -> SET_ITEMS_FOLDER
            is InnerItem -> "$INNER_ITEMS_FOLDER/${item.treasureName}"
            else -> return ""
        }
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

    private fun setBoxToDrawable(iconDrawable: Drawable, item: Item): Drawable {
        val iconBitmap = (iconDrawable as BitmapDrawable).bitmap
        val newWidth = (iconBitmap.width * FRAME_SIZE_MULTIPLIER).toInt()
        val newHeight = (iconBitmap.height * FRAME_SIZE_MULTIPLIER).toInt()

        val color = getColorFromPrefix(item)
        val backgroundBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        backgroundBitmap.eraseColor(color)


        val canvas = Canvas(backgroundBitmap)
        val widthIndent = (iconBitmap.width * ((FRAME_SIZE_MULTIPLIER - 1) / 2)).toInt()
        val heightIndent = (iconBitmap.height * ((FRAME_SIZE_MULTIPLIER - 1) / 2)).toInt()
        iconDrawable.bounds = Rect(
            widthIndent, heightIndent,
            iconBitmap.width + widthIndent, iconBitmap.height + heightIndent
        )
        iconDrawable.draw(canvas)

        return BitmapDrawable(resources, backgroundBitmap)
    }

    private fun getColorFromPrefix(item: Item): Int {
        return when (item.getRarity()) {
            Rarity.COMMON.toString() -> Color.rgb(176, 195, 217)
            Rarity.UNCOMMON.toString() -> Color.rgb(94, 152, 217)
            Rarity.RARE.toString() -> Color.rgb(75, 105, 255)
            Rarity.MYTHICAL.toString() -> Color.rgb(136, 71, 255)
            Rarity.LEGENDARY.toString() -> Color.rgb(211, 44, 230)
            Rarity.IMMORTAL.toString() -> Color.rgb(228, 174, 51)
            Rarity.ARCANA.toString() -> Color.rgb(173, 229, 92)
            else -> Color.rgb(0, 0, 0)
        }
    }
}