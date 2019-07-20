package com.orange_infinity.rileywheelsimulator

import com.orange_infinity.rileywheelsimulator.util.convertFromCamelCase
import org.junit.Assert
import org.junit.Test

class UtilTest {

    @Test
    fun convertFromCamelCaseTest() {
        Assert.assertEquals("Crest of the Wyrm Lords", convertFromCamelCase("CrestOfTheWyrmLords"))
        Assert.assertEquals("Nyx Assassin's Dagon", convertFromCamelCase("NyxAssassinsDagon"))
    }
}