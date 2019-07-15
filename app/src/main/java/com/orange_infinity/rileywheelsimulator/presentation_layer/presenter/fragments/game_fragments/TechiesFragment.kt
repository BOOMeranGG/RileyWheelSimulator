package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.game_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.TechiesImageAdapter
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import android.widget.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.TechiesEngine
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.TechiesGame
import com.orange_infinity.rileywheelsimulator.util.logInf

private const val ROW = 5
private const val COLUMN = 7

class TechiesFragment : Fragment(), TechiesGame {

    private lateinit var gridViewField: GridView

    private val techiesEngine = TechiesEngine(this, COLUMN)

    companion object {
        fun newInstance(): TechiesFragment = TechiesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.techies_fragment, container, false)

        //createGameField()
        gridViewField = v.findViewById(R.id.gvField)
        gridViewField.adapter = TechiesImageAdapter(context)
        gridViewField.numColumns = COLUMN

        gridViewField.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            techiesEngine.clickOnCell(position + 1, 100)
            logInf(MAIN_LOGGER_TAG, "GridView item with position = $position was clicked")
        }

        return v
    }

    override fun winTurn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun winGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}