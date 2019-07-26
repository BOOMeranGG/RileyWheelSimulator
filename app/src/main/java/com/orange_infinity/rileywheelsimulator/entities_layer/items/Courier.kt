package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

enum class Courier(
    private val itemName: String,
    private val cost: Float,
    private val rarity: Rarity = Rarity.MYTHICAL
) : Item {
    CaptainBamboo("Captain Bamboo", 12f),
    YonexsRage("Yonex's Rage", 34f, rarity = Rarity.LEGENDARY),
    Shagbark("Shagbark", 15f, rarity = Rarity.LEGENDARY),
    NimbleBen("Nimble Ben", 2f, rarity = Rarity.LEGENDARY),
    KupuTheMetamorpher("Kupu the Metamorpher", 1f),
    TheLlamaLlama("The Llama Llama", 55f),
    Itsy("Itsy", 32f, rarity = Rarity.LEGENDARY),
    Mok("Mok", 14f, rarity = Rarity.LEGENDARY),
    BlottoAndStick("Blotto and Stick", 6f, rarity = Rarity.LEGENDARY),
    Tinkbot("Tinkbot", 67f),
    AlphidOfLecaciida("Alphid of Lecaciida", 76f),
    WaldiTheFaithful("Waldi the Faithful", 69f, rarity = Rarity.LEGENDARY),
    ArnabusTheFairyRabbit("Arnabus the Fairy Rabbit", 10f),
    Deathripper("Deathripper", 12f),
    CocoTheCourageous("Coco the Courageous", 13f),
    ToryTheSkyGuardian("Tory the Sky Guardian", 31f),
    Throe("Throe", 41f),
    ClucklesTheBrave("Cluckles the Brave", 58f, rarity = Rarity.LEGENDARY),
    Butch("Butch", 21f, rarity = Rarity.LEGENDARY),
    RamnaughtOfUnderwool("Ramnaught of Underwool", 20f),
    PorcinePrincessPenelope("Porcine Princess Penelope", 31f),
    PrismaticDrake("Prismatic Drake", 32f);

    //    private val courierValues: Array<Courier> = values()
    private val random = Random()

    override fun getRandomItem(): Item {
        val courierValues: Array<Courier> = values()
        val randNum = random.nextInt(courierValues.size)
        logInf(MAIN_LOGGER_TAG, "Courier getting random: ${courierValues[randNum].itemName}")
        return courierValues[randNum]
    }

    override fun getName() = this.name

    override fun getItemName(): String = itemName

    override fun getCost(): Float = cost

    override fun getRarity(): String = rarity.toString()
}