package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.orange_infinity.rileywheelsimulator.R

class SingleItemFragment() : Fragment() {

    private lateinit var imgInnerItem: ImageView
    private lateinit var drawable: Drawable

    companion object {
        fun newInstance(drawable: Drawable): SingleItemFragment {
            val fragment = SingleItemFragment()
            fragment.drawable = drawable
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_single_item, container, false)

        imgInnerItem = v.findViewById(R.id.imgInnerItem)
        imgInnerItem.setImageDrawable(drawable)

        return v
    }
}