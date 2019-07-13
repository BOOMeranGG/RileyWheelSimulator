package com.orange_infinity.rileywheelsimulator.entities_layer.resource

class Sound(val assetPath: String, format: String) {

    val name: String
    var soundId: Int? = null

    init {
        val components = assetPath.split("/")
        val fileName = components.last()
        name = fileName.replace(".$format", "")
    }

}