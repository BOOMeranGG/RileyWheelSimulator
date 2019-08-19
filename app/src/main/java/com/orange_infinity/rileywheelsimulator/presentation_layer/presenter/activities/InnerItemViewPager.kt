package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.db.InnerItemsRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.fragments.SingleItemFragment
import com.orange_infinity.rileywheelsimulator.uses_case_layer.TreasureOpenerController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.IconController

const val TREASURE_NAME_INTENT_KEY = "treasureName"

class InnerItemViewPager : AppCompatActivity() {

    private lateinit var openerController: TreasureOpenerController
    private lateinit var iconController: IconController
    private lateinit var viewPager: ViewPager
    private var treasureName: String = ""
    private var itemList = mutableListOf<InnerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_item_view_pager)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        init()

        val fm = supportFragmentManager
        viewPager.adapter = object : FragmentStatePagerAdapter(fm) {

            override fun getItem(position: Int): Fragment {
                val item = itemList[position]
                return SingleItemFragment.newInstance(iconController.getItemIconDrawableWithBox(item)!!)
            }

            override fun getCount(): Int {
                return itemList.size
            }
        }
    }

    private fun init() {
        //imgItem = findViewById(R.id.imgItem)
        viewPager = findViewById(R.id.viewPager)

        openerController = TreasureOpenerController(InnerItemsRepositoryImpl.getInstance(applicationContext))
        iconController = IconController.getInstance(applicationContext)
        treasureName = intent.getStringExtra(TREASURE_NAME_INTENT_KEY)
        itemList = openerController.createItemSet(treasureName).toMutableList()
    }
}
