package com.orange_infinity.rileywheelsimulator.uses_case_layer.resources

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.SoundPool
import com.orange_infinity.rileywheelsimulator.entities_layer.resource.Sound
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logErr
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.io.IOException

private const val SOUND_FOLDER = "sounds"
private const val MAX_SOUNDS = 1

const val SOUND_RILEY_PLAY = "riley_click.mp3"
const val SOUND_MINES_BOOM = "land_mines_boom.mp3"
const val SOUND_SHORT_FIREWORK = "short_firework.mp3"

class SoundPlayer private constructor(context: Context?): SoundPool.OnLoadCompleteListener {

    private val assets: AssetManager = context!!.assets
    private val sounds = mutableListOf<Sound>()
    private val soundPool = SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0)

    companion object {
        private var instance: SoundPlayer? = null

        fun getInstance(context: Context?): SoundPlayer {
            if (instance == null) {
                return SoundPlayer(context)
                    .also { instance = it }
            }
            return instance as SoundPlayer
        }
    }

    init {
        soundPool.setOnLoadCompleteListener(this)
    }

    fun standardPlay(fileName: String) {
        var assetPath = ""
        try {
            assetPath = "$SOUND_FOLDER/$fileName"
            val currentSound = Sound(assetPath, "mp3")
            load(currentSound)
        } catch (e: IOException) {
            logErr(MAIN_LOGGER_TAG, "Can not load file: $assetPath", e)
        }
    }

    fun playWithLoop(fileName: String, loop: Int) {
        val sound: Sound = sounds.find { fileName == it.name } ?: return
        soundPool.play(sound.soundId ?: return, 1.0f, 1.0f, 1, loop, 1.0f)
    }

    override fun onLoadComplete(soundPool: SoundPool, sampleId: Int, status: Int) {
        if (status == 0) {
            soundPool.play(sampleId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    private fun load(sound: Sound) {
        val afd = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }
}
