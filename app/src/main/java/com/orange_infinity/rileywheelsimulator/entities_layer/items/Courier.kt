package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

enum class Courier(private val itemName: String, private val cost: Int) : Item {
    //TODO("Add rarity to couriers")
    CaptainBamboo("Captain Bamboo", 12),
    YonexsRage("Yonex's Rage", 34),
    Shagbark("Shagbark", 15),
    NimbleBen("Nimble Ben", 2),
    KupuTheMetamorpher("Kupu the Metamorpher", 1),
    TheLlamaLlama("The Llama Llama", 55),
    Itsy("Itsy", 32),
    Mok("Mok", 14),
    BlottoAndStick("Blotto and Stick", 6),
    Tinkbot("Tinkbot", 67),
    AlphidOfLecaciida("Alphid of Lecaciida", 76),
    WaldiTheFaithful("Waldi the Faithful", 69),
    ArnabusTheFairyRabbit("Arnabus the Fairy Rabbit", 10),
    Deathripper("Deathripper", 12),
    CocoTheCourageous("Coco the Courageous", 13),
    ToryTheSkyGuardian("Tory the Sky Guardian", 31),
    Throe("Throe", 41),
    ClucklesTheBrave("Cluckles the Brave", 58),
    Butch("Butch", 21),
    RamnaughtOfUnderwool("Ramnaught of Underwool", 20),
    PorcinePrincessPenelope("Porcine Princess Penelope", 31),
    PrismaticDrake("Prismatic Drake", 32);

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

    override fun getCost(): Int = cost
}