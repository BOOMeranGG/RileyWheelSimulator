package com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.orange_infinity.rileywheelsimulator.R
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.PICKED_LIST_NAMING
import com.orange_infinity.rileywheelsimulator.presentation_layer.presenter.dialog_fragments.TOTAL_COST_KEY
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.AsyncFlip
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.CoinFlipRoomCreator
import com.orange_infinity.rileywheelsimulator.uses_case_layer.game_core.coin_flip.getTeamNameFromIndex
import java.lang.RuntimeException
import kotlin.collections.ArrayList

const val TEAM_NAME_KEY = "teamName"
const val IMAGE_ID_KEY = "teamName"

class CoinFlipRoomActivity : AppCompatActivity() {

    private lateinit var imgFirstPlayer: ImageView
    private lateinit var imgSecondPlayer: ImageView
    private lateinit var imgCoin: ImageView
    private lateinit var btnLeave: Button
    private lateinit var btnStart: Button
    private lateinit var prWaitBot: ProgressBar
    private lateinit var pickNamingList: ArrayList<String>
    private lateinit var asyncFlip: AsyncFlip
    private lateinit var roomCreator: CoinFlipRoomCreator

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

        imgFirstPlayer = findViewById(R.id.imgFirstPlayer)
        imgSecondPlayer = findViewById(R.id.imgSecondPlayer)

        imgCoin = findViewById(R.id.imgCoin)
        imgCoin.visibility = ImageView.INVISIBLE
        imgId = intent.getIntExtra(IMAGE_ID_KEY, 0)
        //teamName = intent.getStringExtra(TEAM_NAME_KEY)
        teamName = getTeamNameFromIndex(imgId)
        totalCost = intent.getFloatExtra(TOTAL_COST_KEY, 0f)
        pickNamingList = intent.getStringArrayListExtra(PICKED_LIST_NAMING)

        imgFirstPlayer.setImageResource(imgId)

        btnLeave = findViewById(R.id.btnLeave)
        btnLeave.setOnClickListener {
            finish()
        }
        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            asyncFlip.execute()
            btnStart.isClickable = false
        }

        asyncFlip = AsyncFlip(this)
        roomCreator = CoinFlipRoomCreator(this, teamName)

        prWaitBot = findViewById(R.id.prWaitBot)
        imgSecondPlayer.visibility = ImageView.INVISIBLE

        showBot()
    }

    private fun showBot() {
        roomCreator.playerCreateGame(totalCost)
    }
}
