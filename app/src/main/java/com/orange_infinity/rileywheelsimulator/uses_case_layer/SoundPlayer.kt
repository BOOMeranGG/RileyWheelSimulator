package com.orange_infinity.rileywheelsimulator.uses_case_layer

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import com.orange_infinity.rileywheelsimulator.entities_layer.Sound
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logErr
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.io.IOException

private const val SOUND_FOLDER = "sounds"
private const val MAX_SOUNDS = 2
const val RILEY_PLAY = "riley_click"

//TODO("Make Singleton")
class SoundPlayer(context: Context?) {

    private val assets: AssetManager = context!!.assets
    private val sounds = mutableListOf<Sound>()
    private val soundPool = SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0)

    init {
        loadSounds()
    }

    fun play(fileName: String) {
        val sound: Sound = sounds.find { fileName == it.name } ?: return
        soundPool.play(sound.soundId ?: return, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    private fun loadSounds() {
        val soundNames: Array<String>?
        try {
            soundNames = assets.list(SOUND_FOLDER)
            logInf(MAIN_LOGGER_TAG, "Found ${soundNames.size} sounds")
        } catch (e: IOException) {
            logErr(MAIN_LOGGER_TAG, "Could not list assets", e)
            return
        }

        for (fileName in soundNames) {
            try {
                val assetPath = "$SOUND_FOLDER/$fileName"
                val sound = Sound(assetPath, "mp3")
                load(sound)
                sounds.add(sound)
            } catch (e: IOException) {
                logErr(MAIN_LOGGER_TAG, "Could not load sound $fileName", e)
            }
        }
    }

    private fun load(sound: Sound) {
        val afd = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}