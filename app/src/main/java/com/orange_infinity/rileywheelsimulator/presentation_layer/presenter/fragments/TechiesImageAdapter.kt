package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.orange_infinity.rileywheelsimulator.R
import android.view.LayoutInflater
import android.widget.ImageView

private const val ROW = 5
private const val COLUMN = 7

class TechiesImageAdapter(val context: Context?) : BaseAdapter() {

    val gameFieldImagedList = arrayOf(
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo,
        R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo, R.drawable.dota2_logo
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var grid: View

        if (convertView == null) {
            grid = View(context)
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            grid = inflater.inflate(R.layout.field_techies_item, parent, false)
        } else {
            grid = convertView
        }

        val imageView = grid.findViewById<ImageView>(R.id.imgField)
        imageView.setImageResource(gameFieldImagedList[position])

        return grid
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return gameFieldImagedList[position]
    }

    override fun getCount(): Int {
        return gameFieldImagedList.size
    }
}