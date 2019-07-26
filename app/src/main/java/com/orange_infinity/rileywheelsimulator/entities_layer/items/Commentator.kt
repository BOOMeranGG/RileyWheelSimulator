package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

enum class Commentator(
    private val itemName: String,
    private val cost: Float = 0f,
    private val rarity: Rarity = Rarity.MYTHICAL
) : Item {
    AnnouncerDrKleiner("Announcer: Dr. Kleiner"),
    AnnouncerJuggernaut("Announcer: Juggernaut"),
    AnnouncerNaturesProphet("Announcer: Nature's Prophet"),
    AnnouncerStormSpirit("Announcer: Storm Spirit"),
    AnnouncerAxe("Announcer: Axe"),
    AnnouncerDeathProphet("Announcer: Death Prophet", rarity = Rarity.RARE),
    AnnouncerBastion("Announcer: Bastion"),
    AnnouncerTusk("Announcer: Tusk"),
    AnnouncerPyrionFlax("Announcer: Pyrion Flax"),
    AnnouncerDefenseGrid("Announcer: Defense Grid"),
    AnnouncerTrine("Announcer: Trine"),
    AnnouncerLina("AnnouncerLina"),
    AnnouncerClockwerk("Announcer: Clockwerk"),
    AnnouncerTheStanleyParable("Announcer: The Stanley Parable"),
    AnnouncerTechies("Announcer: Techies"),
    AnnouncerRickAndMorty("Announcer: Rick and Morty"),
    AnnouncerBristleback("Announcer: Bristleback"),
    AnnouncerFallout("Announcer: Fallout 4"),
    MegaKillsJuggernaut("Mega-Kills: Juggernaut", rarity = Rarity.RARE),
    MegaKillsNaturesProphet("Mega-Kills: Nature's Prophet", rarity = Rarity.RARE),
    MegaKillsPirateCaptain("Mega-Kills: Pirate Captain", rarity = Rarity.RARE),
    MegaKillsBastion("Mega-Kills: Bastion"),
    MegaKillsAxe("Mega-Kills: Axe", rarity = Rarity.RARE),
    MegaKillsStormSpirit("Mega-Kills: Storm Spirit", rarity = Rarity.RARE),
    MegaKillsPyrionFlax("Mega-Kills: Pyrion Flax"),
    MegaKillsDefenseGrid("Mega-Kills: Defense Grid"),
    MegaKillsTrine("Mega-Kills: Trine"),
    MegaKillsLina("Mega-Kills: Lina"),
    MegaKillsClockwerk("Mega-Kills: Clockwerk"),
    MegaKillsTheStanleyParable("Mega-Kills: The Stanley Parable"),
    MegaKillsTechies("Mega-Kills: Techies"),
    MegaKillsRickAndMorty("Mega-Kills: Rick and Morty"),
    MegaKillsFallout("Mega-Kills: Fallout 4");

    private val random = Random()

    override fun getRandomItem(): Item {
        val courierValues: Array<Commentator> = values()
        val randNum = random.nextInt(courierValues.size)
        logInf(MAIN_LOGGER_TAG, "Commentator getting random: ${courierValues[randNum].itemName}")
        return courierValues[randNum]
    }

    override fun getName() = this.name

    override fun getItemName(): String = itemName

    override fun getCost(): Float = cost

    override fun getRarity(): String = rarity.toString()
}