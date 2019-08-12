package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import android.view.MotionEvent
import android.view.animation.AlphaAnimation
import android.widget.*
import com.facebook.rebound.*
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.FindPickerRuleFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.ItemPickerFragment
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.SYMBOL_MONEY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.InventoryController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_MINES_BOOM
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero.FindHeroEngine
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.find_hero.FindHeroGame
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.lang.RuntimeException

private const val TENSION = 100.0
private const val FRICTION = 30.0
private const val TURN_TWO_PRIZE = 7.0f
private const val RATE = 1.0f
private const val ITEM_WINNER = "itemWinner"
private const val INFO_RULES = "infoRules"

class PuzzleFragment : Fragment(), View.OnTouchListener, SpringListener, ViewSwitcher.ViewFactory, FindHeroGame,
    View.OnClickListener {

    private lateinit var img1: ImageSwitcher
    private lateinit var img2: ImageSwitcher
    private lateinit var img3: ImageSwitcher
    private lateinit var img4: ImageSwitcher
    private lateinit var img5: ImageSwitcher
    private lateinit var img6: ImageSwitcher
    private lateinit var img7: ImageSwitcher
    private lateinit var img8: ImageSwitcher
    private lateinit var img9: ImageSwitcher
    private lateinit var btnClear: Button
    private lateinit var btnPickUp: Button
    private lateinit var btnRules: Button
    private lateinit var soundPlayer: SoundPlayer
    private lateinit var springSystem: SpringSystem
    private lateinit var spring: Spring
    private lateinit var inventoryController: InventoryController
    private lateinit var userInfoController: UserInfoController

    private var currentImg: ImageSwitcher? = null
    private var currentScaleX: Float = 0f
    private var currentScaleY: Float = 0f
    private var isGameInStageTwo = false

    private var findHeroEngine: FindHeroEngine = FindHeroEngine(this)

    companion object {
        fun newInstance(): PuzzleFragment = PuzzleFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_find_hero, container, false)
        btnClear = v.findViewById(R.id.btnClear)
        btnClear.setOnClickListener {
            prepareNewGame()
        }

        btnPickUp = v.findViewById(R.id.btnPickUp)
        btnPickUp.setOnClickListener(this)
        btnRules = v.findViewById(R.id.btnRules)
        btnRules.setOnClickListener(this)

        setImg(v)
        soundPlayer = SoundPlayer.getInstance(context?.applicationContext)
        currentScaleX = img1.scaleX
        currentScaleY = img1.scaleY

        springSystem = SpringSystem.create()
        spring = springSystem.createSpring()
        spring.addListener(this)
        spring.springConfig = SpringConfig(TENSION, FRICTION)

        findHeroEngine.newGame()
        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(activity?.applicationContext))
        userInfoController = UserInfoController(
            activity, UserPreferencesImpl(), InventoryRepositoryImpl.getInstance(activity?.applicationContext)
        )

        return v
    }

    override fun gameStart(position: Int) {
        userInfoController.changeUserMoney(RATE * -1)
        val item = findHeroEngine.gameField[position] ?: return
        changeIcon(currentImg!!, item)
        logInf(MAIN_LOGGER_TAG, "User rate is $RATE")
    }

    override fun winTurn(position: Int) {
        val item = findHeroEngine.gameField[position] ?: return
        changeIcon(currentImg!!, item)
        isGameInStageTwo = !isGameInStageTwo
    }

    override fun winGame(winningItem: Item) {
        openAllCells(findHeroEngine.gameField)
        soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
        inventoryController.addItem(winningItem)

        val dialog = ItemPickerFragment.newInstance(winningItem, 1)
        dialog.show(activity?.supportFragmentManager, ITEM_WINNER)
    }

    override fun loseGame() {
        openAllCells(findHeroEngine.gameField)
        soundPlayer.standardPlay(SOUND_MINES_BOOM)
        isGameInStageTwo = false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        currentImg?.scaleX = currentScaleX
        currentImg?.scaleY = currentScaleY
        currentImg = v as ImageSwitcher
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

    override fun makeView(): View {
        val img = ImageView(context)
        img.scaleType = ImageView.ScaleType.CENTER_CROP
        return img
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btnPickUp) {
            if (!isGameInStageTwo) {
                return
            }
            userInfoController.changeUserMoney(TURN_TWO_PRIZE)
            prepareNewGame()
            Toast.makeText(context, "You pick up prize $TURN_TWO_PRIZE$SYMBOL_MONEY", Toast.LENGTH_LONG).show()
        } else if (v.id == R.id.btnRules) {
            val dialog = FindPickerRuleFragment.newInstance()
            dialog.show(activity?.supportFragmentManager, INFO_RULES)
            logInf(MAIN_LOGGER_TAG, "Show game rules")
        }
    }

    private fun prepareNewGame() {
        isGameInStageTwo = false
        findHeroEngine.newGame()
        resetImages()
    }

    private fun resetImages() {
        img1.setImageResource(R.drawable.dota2_logo)
        img2.setImageResource(R.drawable.dota2_logo)
        img3.setImageResource(R.drawable.dota2_logo)
        img4.setImageResource(R.drawable.dota2_logo)
        img5.setImageResource(R.drawable.dota2_logo)
        img6.setImageResource(R.drawable.dota2_logo)
        img7.setImageResource(R.drawable.dota2_logo)
        img8.setImageResource(R.drawable.dota2_logo)
        img9.setImageResource(R.drawable.dota2_logo)
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

    private fun changeIcon(img: ImageSwitcher, item: Item) {
        img.setImageDrawable(
            IconController.getInstance(context?.applicationContext)
                .getItemIconDrawable(item)
        )
    }

    private fun getImgPosition(img: ImageSwitcher): Int {
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

        img1.setFactory(this)
        img2.setFactory(this)
        img3.setFactory(this)
        img4.setFactory(this)
        img5.setFactory(this)
        img6.setFactory(this)
        img7.setFactory(this)
        img8.setFactory(this)
        img9.setFactory(this)
        resetImages()
        val inAnimation = AlphaAnimation(0f, 1f)
        inAnimation.duration = 1000
        val outAnimation = AlphaAnimation(1f, 0f)
        outAnimation.duration = 1000

        img1.inAnimation = inAnimation
        img1.outAnimation = outAnimation
        img2.inAnimation = inAnimation
        img2.outAnimation = outAnimation
        img3.inAnimation = inAnimation
        img3.outAnimation = outAnimation
        img4.inAnimation = inAnimation
        img4.outAnimation = outAnimation
        img5.inAnimation = inAnimation
        img5.outAnimation = outAnimation
        img6.inAnimation = inAnimation
        img6.outAnimation = outAnimation
        img7.inAnimation = inAnimation
        img7.outAnimation = outAnimation
        img8.inAnimation = inAnimation
        img8.outAnimation = outAnimation
        img9.inAnimation = inAnimation
        img9.outAnimation = outAnimation


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
