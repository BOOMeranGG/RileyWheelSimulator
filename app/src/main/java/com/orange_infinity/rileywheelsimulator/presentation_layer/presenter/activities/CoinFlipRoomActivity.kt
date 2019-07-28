package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.data_layer.UserPreferencesImpl
import com.orange_infinity.rileywheelsimulator.data_layer.db.InventoryRepositoryImpl
import com.orange_infinity.rileywheelsimulator.entities_layer.items.InnerItem
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.PICKED_LIST_NAMING
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.TOTAL_COST_KEY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.InventoryController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.UserInfoController
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.*
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_MINES_BOOM
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SOUND_SHORT_FIREWORK
import com.orange_infinity.rileywheelsimulator.uses_case_layer.resources.SoundPlayer
import kotlin.collections.ArrayList

const val TEAM_NAME_KEY = "teamName"
const val IMAGE_ID_KEY = "imageId"

class CoinFlipRoomActivity : AppCompatActivity(), View.OnClickListener, AsyncFlip.GameResultListener {

    private lateinit var imgFirstPlayer: ImageView
    private lateinit var imgSecondPlayer: ImageView
    private lateinit var imgCoin: ImageView
    private lateinit var btnLeave: Button
    private lateinit var btnStart: Button
    private lateinit var pickNamingList: ArrayList<String>
    private lateinit var asyncFlip: AsyncFlip
    private lateinit var roomCreator: CoinFlipRoomCreator
    private lateinit var botAccount: BotAccount
    private lateinit var userInfoController: UserInfoController
    private lateinit var inventoryController: InventoryController

    private var imgId: Int = 0
    private var totalCost: Float = 0f
    private var teamName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_flip_room)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        init()

        imgId = intent.getIntExtra(IMAGE_ID_KEY, 0)
        teamName = intent.getStringExtra(TEAM_NAME_KEY)
        //teamName = getTeamNameFromIndex(imgId)
        totalCost = intent.getFloatExtra(TOTAL_COST_KEY, 0f)
        pickNamingList = intent.getStringArrayListExtra(PICKED_LIST_NAMING)

        imgFirstPlayer.setImageResource(imgId)

        roomCreator = CoinFlipRoomCreator(this, teamName, totalCost)
        createBot()
        asyncFlip = AsyncFlip(this)
    }

    private fun createBot() {
        botAccount = roomCreator.joinBotInRoom(totalCost)
    }

    override fun onPause() {
        super.onPause()
        asyncFlip.cancel(true)
    }

    override fun onClick(v: View?) {
        asyncFlip.execute(botAccount)
        btnStart.isClickable = false
    }

    override fun playerWinner() {
        val soundPlayer = SoundPlayer.getInstance(applicationContext)
        Toast.makeText(this, "YOU ARE WINNER!", Toast.LENGTH_LONG).show()
        userInfoController.changeUserMoney(botAccount.money)
        soundPlayer.standardPlay(SOUND_SHORT_FIREWORK)
    }

    override fun botWinner() {
        val soundPlayer = SoundPlayer.getInstance(applicationContext)
        pickNamingList.forEach {
            inventoryController.deleteItem(InnerItem(name = it))
        }
        Toast.makeText(this, "YOU LOSE ;(", Toast.LENGTH_LONG).show()
        soundPlayer.standardPlay(SOUND_MINES_BOOM)
    }

    private fun init() {
        imgFirstPlayer = findViewById(R.id.imgFirstPlayer)
        imgSecondPlayer = findViewById(R.id.imgSecondPlayer)
        imgSecondPlayer.visibility = ImageView.INVISIBLE

        imgCoin = findViewById(R.id.imgCoin)
        imgCoin.visibility = ImageView.INVISIBLE

        btnLeave = findViewById(R.id.btnLeave)
        btnLeave.setOnClickListener {
            finish()
        }
        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener(this)

        userInfoController = UserInfoController(
            this, UserPreferencesImpl(), InventoryRepositoryImpl.getInstance(applicationContext)
        )
        inventoryController = InventoryController(InventoryRepositoryImpl.getInstance(applicationContext))
    }
}
