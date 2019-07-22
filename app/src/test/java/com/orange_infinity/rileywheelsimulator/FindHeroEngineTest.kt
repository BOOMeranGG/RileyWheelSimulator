package com.orange_infinity.rileywheelsimulator

import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments.PuzzleFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero.FindHeroEngine
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [23])
class FindHeroEngineTest {

    @Test
    fun `testEngine`() {
        val testGame = PuzzleFragment()
        val gameEngine = FindHeroEngine(testGame)
        gameEngine.newGame()
        val fieldEngine = arrayOf(false, false, false, false, false, false, false, false, false)

        assertEquals(fieldEngine.size, gameEngine.fieldVisited.size)
        for (i in 0 until fieldEngine.size) {
            assertEquals(fieldEngine[i], gameEngine.fieldVisited[i])
        }
    }
}