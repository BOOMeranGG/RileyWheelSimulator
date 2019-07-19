package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

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
import com.orange_infinity.rileywheelsimulator.uses_case_layer.MINES_BOOM
import com.orange_infinity.rileywheelsimulator.uses_case_layer.SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.SoundPlayer
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.techies.TechiesEngine
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.techies.TechiesGame
import com.orange_infinity.rileywheelsimulator.util.CASINO_LOGGER_TAG

private const val ROW = 5
private const val COLUMN = 7

class TechiesFragment : Fragment(),
    TechiesGame {

    private lateinit var gameFieldImageIddList: Array<Int>
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnClear: Button

    private val techiesEngine = TechiesEngine(this, COLUMN)
    private lateinit var soundPlayer: SoundPlayer
    private var winWidth: Int = 0
    private var winHeight: Int = 0

    companion object {
        fun newInstance(): TechiesFragment = TechiesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val display = activity!!.windowManager.defaultDisplay
        val metricsDisplay = DisplayMetrics()
        display.getMetrics(metricsDisplay)
        soundPlayer = SoundPlayer.getInstance(context)

        winWidth = (metricsDisplay.widthPixels / 1.25).toInt() / 7
        winHeight = (metricsDisplay.heightPixels / 1.25).toInt() / 6
        initGameField()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_techies_holder, container, false)

        recyclerView = v.findViewById(R.id.fieldRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 7)
        recyclerView.adapter = FieldAdapter(gameFieldImageIddList)

        btnClear = v.findViewById(R.id.btnClear)
        btnClear.setOnClickListener {
            initGameField()
            updateRecycler()
            techiesEngine.prepareNewGame()
        }

        return v
    }

    override fun winTurn(position: Int) {
        logInf(CASINO_LOGGER_TAG, "winTurn() is called")
        openCells()
    }

    override fun winGame() {
        Toast.makeText(context, "You are WINNER! +${techiesEngine.getPrize()}$", Toast.LENGTH_LONG).show()
        openCells()
        soundPlayer.standardPlay(SHORT_FIREWORK)
        techiesEngine.prepareNewGame()
    }

    //TODO("Довольно костыльная реализация. Исправить(открытие после проигрыша)")
    override fun loseGame(position: Int) {
        openCells()
        soundPlayer.standardPlay(MINES_BOOM)
        techiesEngine.gameStage++
        while (techiesEngine.gameStage <= COLUMN) {
            techiesEngine.mineIndex = techiesEngine.getMinePosition()
            openCells()
            techiesEngine.gameStage++
        }
        techiesEngine.prepareNewGame()
        Toast.makeText(context, "You DEFEAT", Toast.LENGTH_SHORT).show()
    }

    private fun updateRecycler() {
        val adapter = recyclerView.adapter as FieldAdapter
        adapter.fields = gameFieldImageIddList
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun openCells() {
        val mineIndex = techiesEngine.mineIndex
        val stage = techiesEngine.gameStage
        var index = stage - 1
        for (i in 0 until ROW) {
            gameFieldImageIddList[index] = R.drawable.test_logo
            index += COLUMN
        }
        gameFieldImageIddList[mineIndex] = R.drawable.treasure_logo
        updateRecycler()
    }

    private fun initGameField() {
        gameFieldImageIddList = arrayOf(
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
            R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo
        )
    }

    // -----------------------------------------------------------------------------------------------------------------
    private inner class FieldHolder(inflater: LayoutInflater, container: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_techies_field, container, false)),
        View.OnClickListener {

        private val imgField: ImageView = itemView.findViewById(R.id.imgField2)
        private val imgLayoutParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).also { it.width = winWidth; it.height = winHeight; }

        fun bindItem(fieldId: Int) {
            imgField.setImageResource(fieldId)
            imgField.layoutParams = imgLayoutParam
            imgField.scaleType = ImageView.ScaleType.FIT_XY
            imgField.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            logInf(MAIN_LOGGER_TAG, "Field was clicked $adapterPosition")
            techiesEngine.clickOnCell(adapterPosition, 100.0f)
        }
    }

    private inner class FieldAdapter(var fields: Array<Int>) : RecyclerView.Adapter<FieldHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldHolder {
            val inflater = LayoutInflater.from(activity)
            return FieldHolder(inflater, parent)
        }

        override fun onBindViewHolder(fieldHolder: FieldHolder, position: Int) {
            fieldHolder.bindItem(fields[position])
        }

        override fun getItemCount(): Int {
            return fields.size
        }
    }
}