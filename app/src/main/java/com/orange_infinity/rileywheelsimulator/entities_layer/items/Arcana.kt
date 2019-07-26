package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*
import android.os.Parcel
import android.os.Parcelable

enum class Arcana(private val itemName: String, private val cost: Float, val hero: Heroes, val rarity: Rarity) : Item {
    BladesOfVothDomosh("Blades of Voth Domosh", cost = 10f, hero = Heroes.LegionCommander, rarity = Rarity.ARCANA),
    DemonEater("Demon Eater", cost = 7f, hero = Heroes.ShadowFiend, rarity = Rarity.ARCANA),
    FierySoulOfTheSlayer("Fiery Soul of the Slayer", cost = 14f, hero = Heroes.Lina, rarity = Rarity.ARCANA),
    FractalHornsOfInnerAbysm("Fractal Horns of Inner Abysm", cost = 6f, hero = Heroes.Terrorblade, rarity = Rarity.ARCANA),
    FrostAvalanche("Frost Avalanche", cost = 24f, hero = Heroes.CrystalMaiden, rarity = Rarity.ARCANA),
    ManifoldParadox("Manifold Paradox", cost = 7f, hero = Heroes.PhantomAssassin, rarity = Rarity.ARCANA),
    SwineOfYheSunkenGalley("Swine of the Sunken Galley", cost = 8f, hero = Heroes.Techies, rarity = Rarity.ARCANA),
    TempestHelmOfTheThundergod("Tempest Helm of the Thundergod", cost = 10f, hero = Heroes.Zeus, rarity = Rarity.ARCANA);

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

    override fun getCost(): Float = cost

    override fun getRarity(): String = rarity.toString()
}