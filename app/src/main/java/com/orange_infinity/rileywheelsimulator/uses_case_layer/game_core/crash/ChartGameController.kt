package com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.crash

class ChartGameController {
    
    private val chanceToCrossNextInt = 0.45

    fun getMultiplier(): Double {
        var int = 1
        while (Math.random() <= chanceToCrossNextInt) {
            int++
        }
        return int + Math.random()
    }
}