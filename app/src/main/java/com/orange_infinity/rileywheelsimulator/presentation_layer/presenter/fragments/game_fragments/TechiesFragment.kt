package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import android.util.DisplayMetrics
import android.widget.*
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_MINES_BOOM
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.techies.TechiesEngine
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.techies.TechiesGame
import com.orange_infinity.rileywheelsimulator.util.CASINO_LOGGER_TAG

private const val ROW = 5
private const val COLUMN = 7

class TechiesFragment : Fragment(), TechiesGame, View.OnClickListener {

    private lateinit var gameFieldBackgroundIddList: Array<Int>
    private lateinit var gameFieldMineIdArray: Array<Int?>
    private lateinit var userInfoController: UserInfoController
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnClear: Button
    private lateinit var btnAddTen: Button
    private lateinit var btnAddFifty: Button
    private lateinit var btnAddHundred: Button
    private lateinit var tvMoneyBet: TextView

    private val techiesEngine = TechiesEngine(this, COLUMN)
    private lateinit var soundPlayer: SoundPlayer
    private var winWidth: Int = 0
    private var winHeight: Int = 0
    private var currentBet: Float = 0f

    companion object {
        fun newInstance(): TechiesFragment = TechiesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = activity!!.windowManager.defaultDisplay
        val metricsDisplay = DisplayMetrics()
        display.getMetrics(metricsDisplay)
        soundPlayer = SoundPlayer.getInstance(context?.applicationContext)

        winWidth = (metricsDisplay.widthPixels / 1.25).toInt() / 7
        winHeight = (metricsDisplay.heightPixels / 1.25).toInt() / 6
        initGameField()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_techies_holder, container, false)

        recyclerView = v.findViewById(R.id.fieldRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 7)
        recyclerView.adapter = FieldAdapter(gameFieldBackgroundIddList, gameFieldMineIdArray)

        btnAddTen = v.findViewById(R.id.btnAddTen)
        btnAddFifty = v.findViewById(R.id.btnAddFifty)
        btnAddHundred = v.findViewById(R.id.btnAddHundred)
        btnAddTen.setOnClickListener(this)
        btnAddFifty.setOnClickListener(this)
        btnAddHundred.setOnClickListener(this)

        tvMoneyBet = v.findViewById(R.id.tvMoneyBet)
        btnClear = v.findViewById(R.id.btnClear)
        btnClear.setOnClickListener {
            initGameField()
            updateRecycler()
            isAddMoneyBtnEnabled(true)
            if (techiesEngine.gameStage != 0) {
                userInfoController.changeUserMoney(techiesEngine.getPrize())
                Toast.makeText(context, "You are WINNER! +${techiesEngine.getPrize()}$", Toast.LENGTH_LONG).show()
                techiesEngine.prepareNewGame()
                isAddMoneyBtnEnabled(true)
            }
            currentBet = 0f
            tvMoneyBet.text = "$currentBet$"
        }

        userInfoController = UserInfoController(
            activity, UserPreferencesImpl(), InventoryRepositoryImpl.getInstance(activity?.applicationContext)
        )

        return v
    }

    @SuppressLint("SetTextI18n")
    override fun winTurn(position: Int) {
        isAddMoneyBtnEnabled(false)
        logInf(CASINO_LOGGER_TAG, "winTurn() is called")
        deleteMoneyIfItIsFirstTurn()
        currentBet = techiesEngine.getPrize()
        tvMoneyBet.text = "$currentBet$"
        openCells(false)
    }

    override fun winGame() {
        Toast.makeText(context, "You are WINNER! +${techiesEngine.getPrize()}$", Toast.LENGTH_LONG).show()
        openCells(false)
        soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
        userInfoController.changeUserMoney(techiesEngine.getPrize())
        techiesEngine.prepareNewGame()
    }

    //TODO("Довольно костыльная реализация. Исправить(открытие после проигрыша)")
    override fun loseGame(position: Int) {
        openCells(true)
        deleteMoneyIfItIsFirstTurn()
        soundPlayer.standardPlay(SOUND_MINES_BOOM)
        techiesEngine.gameStage++
        while (techiesEngine.gameStage <= COLUMN) {
            techiesEngine.mineIndex = techiesEngine.getMinePosition()
            openCells(false)
            techiesEngine.gameStage++
        }
        techiesEngine.prepareNewGame()
        Toast.makeText(context, "You DEFEAT", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View) {
        val restOfMoney = userInfoController.getUserMoney() - currentBet
        when (v.id) {
            btnAddTen.id -> {
                if (restOfMoney < 10)
                    return
                currentBet+= 10
            }
            btnAddFifty.id -> {
                if (restOfMoney < 50)
                    return
                currentBet += 50
            }
            btnAddHundred.id -> {
                if (restOfMoney < 100)
                    return
                currentBet += 100
            }
        }
        tvMoneyBet.text = "$currentBet$"
    }

    private fun isAddMoneyBtnEnabled(isEnable: Boolean) {
        btnAddTen.isEnabled = isEnable
        btnAddFifty.isEnabled = isEnable
        btnAddHundred.isEnabled = isEnable
    }

    private fun deleteMoneyIfItIsFirstTurn() {
        if (techiesEngine.gameStage == 1) {
            userInfoController.changeUserMoney((currentBet * -1))
        }
    }

    private fun updateRecycler() {
        val adapter = recyclerView.adapter as FieldAdapter
        adapter.fields = gameFieldBackgroundIddList
        adapter.gameFieldMine = gameFieldMineIdArray
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun openCells(isDeadInThisTurn: Boolean) {
        val mineIndex = techiesEngine.mineIndex
        if (isDeadInThisTurn) {
            gameFieldMineIdArray[mineIndex] = R.drawable.mine_boom
        } else {
            gameFieldMineIdArray[mineIndex] = R.drawable.mine
        }
        updateRecycler()
    }

    private fun initGameField() {
        gameFieldBackgroundIddList = arrayOf(
            R.drawable.mine_01, R.drawable.mine_02, R.drawable.mine_03, R.drawable.mine_04,
            R.drawable.mine_05, R.drawable.mine_06, R.drawable.mine_07, R.drawable.mine_08,
            R.drawable.mine_09, R.drawable.mine_10, R.drawable.mine_11, R.drawable.mine_12,
            R.drawable.mine_13, R.drawable.mine_14, R.drawable.mine_15, R.drawable.mine_16,
            R.drawable.mine_17, R.drawable.mine_18, R.drawable.mine_19, R.drawable.mine_20,
            R.drawable.mine_21, R.drawable.mine_22, R.drawable.mine_23, R.drawable.mine_24,
            R.drawable.mine_25, R.drawable.mine_26, R.drawable.mine_27, R.drawable.mine_28,
            R.drawable.mine_29, R.drawable.mine_30, R.drawable.mine_31, R.drawable.mine_32,
            R.drawable.mine_33, R.drawable.mine_34, R.drawable.mine_35
        )
        gameFieldMineIdArray = arrayOf(
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
        )
    }

    // -----------------------------------------------------------------------------------------------------------------
    private inner class FieldHolder(inflater: LayoutInflater, container: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_techies_field, container, false)),
        View.OnClickListener {

        private val imgField: ImageView = itemView.findViewById(R.id.imgField2)
        private val imgMine: ImageView = itemView.findViewById(R.id.imgField3)
        private val imgLayoutParam = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).also { it.width = winWidth; it.height = winHeight; }

        fun bindItem(fieldId: Int, mineId: Int?) {
            imgField.setImageResource(fieldId)
            if (mineId != null) {
                imgMine.setImageResource(mineId)
            } else {
                imgMine.setImageDrawable(null)
            }
            imgField.setPadding(2, 2, 2, 2)
            imgField.layoutParams = imgLayoutParam
            imgMine.layoutParams = imgLayoutParam
            imgField.scaleType = ImageView.ScaleType.FIT_XY
            imgMine.scaleType = ImageView.ScaleType.FIT_XY
            imgField.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            logInf(MAIN_LOGGER_TAG, "Field was clicked $adapterPosition")
            techiesEngine.clickOnCell(adapterPosition, currentBet)
        }
    }

    private inner class FieldAdapter(var fields: Array<Int>, var gameFieldMine: Array<Int?>) :
        RecyclerView.Adapter<FieldHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldHolder {
            val inflater = LayoutInflater.from(activity)
            return FieldHolder(inflater, parent)
        }

        override fun onBindViewHolder(fieldHolder: FieldHolder, position: Int) {
            fieldHolder.bindItem(fields[position], gameFieldMine[position])
        }

        override fun getItemCount(): Int {
            return fields.size
        }
    }
}