package com.orange_infinity.rileywheelsimulator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.ItemBox
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Item
import com.orange_infinity.rileywheelsimulator.uses_case_layer.IconController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.InventoryController
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf

class ChooseItemCoinPickActivity : AppCompatActivity() {

    private lateinit var pickItemListLayout: GridLayout
    private lateinit var inventoryRecycler: RecyclerView
    private lateinit var inventoryController: InventoryController
    private lateinit var iconController: IconController
    private var items = mutableListOf<ItemBox>()
    private var pickItemList = mutableListOf<Item>()
    private var countOfPickedItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_item_coin_pick)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        init()
        inventoryRecycler.adapter = ItemAdapter(items)
    }

    private fun updateRecycler() {
        val adapter = inventoryRecycler.adapter as ItemAdapter
        adapter.items = items
        inventoryRecycler.adapter!!.notifyDataSetChanged()
    }

    private fun init() {
        inventoryRecycler = findViewById(R.id.inventoryRecycler)
        inventoryRecycler.layoutManager = GridLayoutManager(this, 2)

        pickItemListLayout = findViewById(R.id.pickItemList)
        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(applicationContext))
        iconController = IconController.getInstance(applicationContext)
        items = inventoryController.getItemsFromInventory().toMutableList()
    }

    //TODO("Сделать width и height в зависимости от экрана")
    private fun addItemToPickItemList(itemPicked: Item) {
        val newImg = ImageView(this@ChooseItemCoinPickActivity)
        newImg.setImageDrawable(iconController.getItemIconDrawable(itemPicked))
        newImg.setOnClickListener {
            logInf(MAIN_LOGGER_TAG, "Return item \"${itemPicked.getItemName()}\" to inventoryViewer")
            returnItemToInventoryViewer(itemPicked)
        }
        addImgToPickItemListLayout(newImg)
    }

    private fun returnItemToInventoryViewer(item: Item) {
        pickItemList.remove(item)
        countOfPickedItems--
        for (i in 0 until items.size) {
            if (items[i].item.getName() == item.getName()) {
                items[i].count++
                updateRecycler()
                updatePickItemList()
                return
            }
        }
        items.add(ItemBox(item, 1))
        items.sort()
        updatePickItemList()
        updateRecycler()
    }

    private fun updatePickItemList() {
        pickItemListLayout.removeAllViews()
        for (itemPicked in pickItemList) {
            addItemToPickItemList(itemPicked)
        }
    }

    private fun addImgToPickItemListLayout(newImg: ImageView) {
        val dp = resources.displayMetrics.density
        val layoutParams = GridLayout.LayoutParams()
        val marginDp = (8 * dp).toInt()
        layoutParams.setMargins(marginDp, 0, marginDp, marginDp)
        layoutParams.width = (75 * dp).toInt()
        layoutParams.height = (75 * dp).toInt()
        newImg.layoutParams = layoutParams

        pickItemListLayout.addView(newImg)
    }

    //------------------------------------------------------------------------------------------------------------------
    private inner class ItemHolder(inflater: LayoutInflater, container: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_inventory, container, false)),
        View.OnClickListener {

        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvCount: TextView = itemView.findViewById(R.id.tvCount)
        private val imgItem: ImageView = itemView.findViewById(R.id.imgItem)
        private val itemLayout: LinearLayout = itemView.findViewById(R.id.itemLayout)
        private lateinit var item: Item
        var itemCount: Int = 1

        @SuppressLint("SetTextI18n")
        fun bindItem(itemBox: ItemBox) {
            item = itemBox.item
            itemCount = itemBox.count
            tvItemName.text = "${itemBox.item.getItemName()}\n${itemBox.item.getCost()}\$"
            tvCount.text = "X${itemBox.count}"
            itemLayout.setOnClickListener(this)

            val icon = iconController.getItemIconDrawable(item)
            if (icon != null) {
                imgItem.setImageDrawable(icon)
            } else {
                imgItem.setImageResource(R.drawable.dota2_logo)
            }
        }

        override fun onClick(v: View?) {
            logInf(MAIN_LOGGER_TAG, "Item \"${item.getItemName()}\" was clicked")
            if (countOfPickedItems >= 9) {
                return
            }
            countOfPickedItems++
            val itemPicked = removeItemFromRecycler() ?: return
            pickItemList.add(itemPicked)
            addItemToPickItemList(itemPicked)
            updateRecycler()
        }

        private fun removeItemFromRecycler(): Item? {
            for (i in 0 until items.size) {
                if (items[i].item.getName() == item.getName()) {
                    val itemPick = items[i].item
                    if (items[i].count > 1) {
                        items[i].count--
                    } else {
                        items.removeAt(i)
                    }
                    return itemPick
                }
            }
            return null
        }
    }

    private inner class ItemAdapter(var items: List<ItemBox>) : RecyclerView.Adapter<ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val inflater = LayoutInflater.from(this@ChooseItemCoinPickActivity)
            return ItemHolder(inflater, parent)
        }

        override fun onBindViewHolder(itemHolder: ItemHolder, position: Int) {
            val item = items[position]
            itemHolder.bindItem(item)
        }

        override fun getItemCount(): Int = items.size
    }
}
