package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Arcana
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Courier
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.entities_layer.resource.Icon
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logErr
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.io.IOException
import java.io.InputStream

private const val ITEM_FOLDER = "items"

class IconController private constructor(context: Context?) {

    private val assets: AssetManager = context!!.assets

    //TODO("Грузит каждый раз заново. ИСПРАВИТЬ!")
    companion object {
        private var instance: IconController? = null

        fun getInstance(context: Context?): IconController {
            if (instance == null) {
                return IconController(context)
            }
            return instance as IconController
        }
    }

    fun getItemIconDrawable(item: Item): Drawable? {
        if (item is Arcana)
            return getDrawableFromAsset("/arcana", item.getName())
        if (item is Courier)
            return getDrawableFromAsset("/courier", item.getName())
        return null
    }

    private fun getDrawableFromAsset(partFolder: String, fileName: String): Drawable? {
        var inputStream: InputStream? = null

        try {
            val assetPath = "${ITEM_FOLDER + partFolder}/$fileName.png"
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