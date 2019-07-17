package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.orange_infinity.rileywheelsimulator.R
import android.view.MotionEvent
import com.facebook.rebound.*
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.uses_case_layer.IconController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.MINES_BOOM
import com.orange_infinity.rileywheelsimulator.uses_case_layer.SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.SoundPlayer
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero.FindHeroEngine
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero.FindHeroGame
import java.lang.RuntimeException

private const val TENSION = 100.0
private const val FRICTION = 30.0

class PuzzleFragment : Fragment(), View.OnTouchListener, SpringListener, FindHeroGame {

    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var img4: ImageView
    private lateinit var img5: ImageView
    private lateinit var img6: ImageView
    private lateinit var img7: ImageView
    private lateinit var img8: ImageView
    private lateinit var img9: ImageView
    private lateinit var springSystem: SpringSystem
    private lateinit var spring: Spring

    private var currentImg: ImageView? = null
    private var currentScaleX: Float = 0f
    private var currentScaleY: Float = 0f

    private var findHeroEngine: FindHeroEngine = FindHeroEngine(this)

    companion object {
        fun newInstance(): PuzzleFragment = PuzzleFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_puzzle, container, false)

        setImg(v)

        currentScaleX = img1.scaleX
        currentScaleY = img1.scaleY

        springSystem = SpringSystem.create()
        spring = springSystem.createSpring()
        spring.addListener(this)
        spring.springConfig = SpringConfig(TENSION, FRICTION)

        findHeroEngine.newGame()

        return v
    }

    override fun winTurn(position: Int) {
        val item = findHeroEngine.gameField[position] ?: return
        changeIcon(currentImg!!, item)
    }

    override fun winGame() {
        openAllCells(findHeroEngine.gameField)
        SoundPlayer.getInstance(context).play(SHORT_FIREWORK)
        //findHeroEngine.newGame()
    }

    override fun loseGame() {
        openAllCells(findHeroEngine.gameField)
        SoundPlayer.getInstance(context).play(MINES_BOOM)
        //findHeroEngine.newGame()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        currentImg?.scaleX = currentScaleX
        currentImg?.scaleY = currentScaleY
        currentImg = v as ImageView
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                spring.endValue = 1.0
                return true
            }
            MotionEvent.ACTION_UP -> {
                spring.endValue = 0.0
                //v.setImageResource(R.drawable.treasure_logo)
                findHeroEngine.pick(getImgPosition(currentImg!!))
                return true
            }
        }
        v.performClick()
        return false
    }

    override fun onSpringUpdate(spring: Spring) {
        val value = spring.currentValue.toFloat()
        val scale = 1f - value * 0.5f
        currentImg?.scaleX = scale
        currentImg?.scaleY = scale
    }

    override fun onSpringAtRest(spring: Spring?) {
    }

    override fun onSpringEndStateChange(spring: Spring?) {
    }

    override fun onSpringActivate(spring: Spring?) {
    }

    private fun openAllCells(field: Array<Item?>) {
        changeIcon(img1, field[0]!!)
        changeIcon(img2, field[1]!!)
        changeIcon(img3, field[2]!!)
        changeIcon(img4, field[3]!!)
        changeIcon(img5, field[4]!!)
        changeIcon(img6, field[5]!!)
        changeIcon(img7, field[6]!!)
        changeIcon(img8, field[7]!!)
        changeIcon(img9, field[8]!!)
    }

    private fun changeIcon(img: ImageView, item: Item) {
        img.setImageDrawable(IconController.getInstance(context?.applicationContext)
            .getItemIconDrawable(item))
    }

    private fun getImgPosition(img: ImageView): Int {
        when (img.id) {
            img1.id -> return 0
            img2.id -> return 1
            img3.id -> return 2
            img4.id -> return 3
            img5.id -> return 4
            img6.id -> return 5
            img7.id -> return 6
            img8.id -> return 7
            img9.id -> return 8
        }
        throw RuntimeException()
    }

    private fun setImg(v: View) {
        img1 = v.findViewById(R.id.img1)
        img2 = v.findViewById(R.id.img2)
        img3 = v.findViewById(R.id.img3)
        img4 = v.findViewById(R.id.img4)
        img5 = v.findViewById(R.id.img5)
        img6 = v.findViewById(R.id.img6)
        img7 = v.findViewById(R.id.img7)
        img8 = v.findViewById(R.id.img8)
        img9 = v.findViewById(R.id.img9)

        img1.setOnTouchListener(this)
        img2.setOnTouchListener(this)
        img3.setOnTouchListener(this)
        img4.setOnTouchListener(this)
        img5.setOnTouchListener(this)
        img6.setOnTouchListener(this)
        img7.setOnTouchListener(this)
        img8.setOnTouchListener(this)
        img9.setOnTouchListener(this)
    }
}
