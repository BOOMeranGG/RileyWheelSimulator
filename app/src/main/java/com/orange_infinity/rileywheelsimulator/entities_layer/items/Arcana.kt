package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

enum class Arcana(private val itemName: String, private val cost: Int, val hero: Heroes) : Item {
    BladesOfVothDomosh("Blades of Voth Domosh", cost = 10, hero = Heroes.LegionCommander),
    DemonEater("Demon Eater", cost = 7, hero = Heroes.ShadowFiend),
    FierySoulOfTheSlayer("Fiery Soul of the Slayer", cost = 14, hero = Heroes.Lina),
    FractalHornsOfInnerAbysm("Fractal Horns of Inner Abysm", cost = 6, hero = Heroes.Terrorblade),
    FrostAvalanche("Frost Avalanche", cost = 24, hero = Heroes.CrystalMaiden),
    ManifoldParadox("Manifold Paradox", cost = 7, hero = Heroes.PhantomAssassin),
    SwineOfYheSunkenGalley("Swine of the Sunken Galley", cost = 8, hero = Heroes.Techies),
    TempestHelmOfTheThundergod("Tempest Helm of the Thundergod", cost = 10, hero = Heroes.Zeus);

//    private val arcanaValues: Array<Arcana> = values()
    private val random = Random()

    override fun getRandomItem(): Item {
        val arcanaValues: Array<Arcana> = values()
        val randNum = random.nextInt(arcanaValues.size)
        logInf(MAIN_LOGGER_TAG, "Arcana getting random: ${arcanaValues[randNum].itemName}")
        return arcanaValues[randNum]
    }

    override fun getName() = this.name

    override fun getItemName(): String = itemName

    override fun getCost(): Int = cost
}