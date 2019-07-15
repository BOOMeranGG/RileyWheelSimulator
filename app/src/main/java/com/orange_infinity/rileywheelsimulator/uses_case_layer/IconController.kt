package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import android.content.res.AssetManager
import android.graphics.drawable.Drawable
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
    private val icons = mutableListOf<Icon>()

    companion object {
        private var instance: IconController? = null

        fun getInstance(context: Context?): IconController {
            if (instance == null) {
                return IconController(context)
            }
            return instance as IconController
        }
    }

    init {
        loadDrawables()
    }

    fun getItemIconDrawable(item: Item): Drawable? = icons.find { item.getName() == it.name }?.drawable

    private fun loadDrawables() {
        val drawableNames: Array<String>?
        try {
            drawableNames = assets.list(ITEM_FOLDER)
            logInf(MAIN_LOGGER_TAG, "Found ${drawableNames.size} icons")
        } catch (e: IOException) {
            logErr(MAIN_LOGGER_TAG, "Cannot found in assets/$ITEM_FOLDER/$", e)
            return
        }

        var inputStream: InputStream? = null
        for (fileName in drawableNames) {
            try {
                val assetPath = "$ITEM_FOLDER/$fileName"
                inputStream = assets.open(assetPath)
                icons.add(Icon(Drawable.createFromStream(inputStream, null), "${fileName.replace(".png", "")}"))
            } catch (e: IOException) {
                logErr(MAIN_LOGGER_TAG, "Could not load icon $fileName", e)
            } finally {
                inputStream?.close()
            }
        }
    }
}