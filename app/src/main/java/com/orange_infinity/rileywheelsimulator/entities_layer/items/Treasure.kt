package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.io.Serializable
import java.util.*

enum class Treasure(
    private val itemName: String,
    private val rarity: Rarity = Rarity.MYTHICAL
) : Item, Serializable {
    LocklessLuckbox("Lockless Luckbox"),
    LocklessLuckvase("Lockless Luckvase"),
    LocklessLuckvase2016("Lockless Luckvase 2016", Rarity.IMMORTAL),
    LocklessLuckvase2015("Lockless Luckvase 2015", Rarity.IMMORTAL),
    //NestedTreasureII("Nested Treasure II", Rarity.RARE),//
    BlessedLuckvessel("Blessed Luckvessel"),
    GhastlyTreasureOfDiretide("Ghastly Treasure of Diretide", Rarity.COMMON),
    QuirtsSummerStash("Quirt's Summer Stash", Rarity.COMMON),
    SithilsSummerStash("Sithil's Summer Stash", Rarity.COMMON),
    TroveCask("Trove Cask", Rarity.IMMORTAL),
    SapphireCask("Sapphire Cask"), //
//    SculptorsPillar("Sculptor's Pillar", Rarity.UNCOMMON),
//    SoaringBonusCache("Soaring Bonus Cache"),
//    SculptorsMonument("Sculptor's Monument"),
//    SculptorsPillar2015("Sculptor's Pillar 2015"),
    GenuineTreasureOfTheShatteredHourglass("Genuine Treasure of the Shattered Hourglass", Rarity.UNCOMMON),
//    FlutteringCache("Fluttering Cache"),
//    FlutteringBonusCache("Fluttering Bonus Cache"),
//    TreasureOfChampions("Treasure of Champions", Rarity.COMMON),
//    TreasureOfDireArms("Treasure of Dire Arms"),
//    TreasureOfTheClovenWorld("Treasure of the Cloven World"),
//    TroveCarafe("Trove Carafe", Rarity.IMMORTAL),
//    TroveCarafe2016("Trove Carafe 2016", Rarity.IMMORTAL),
//    TroveCarafe2015("Trove Carafe 2015", Rarity.IMMORTAL),
//    TreasureOfTheShaperDivine("Treasure of the Shaper Divine", Rarity.COMMON),
//    TreasureOfTheRamsRenewal("Treasure of the Ram's Renewal", Rarity.RARE),
//    TreasureOfTheMendersPalm("Treasure of the Mender's Palm", Rarity.UNCOMMON),
//    TreasureOfTheGrimsneersStash("Treasure of the Grimsneer's Stash", Rarity.RARE),
//    TreasureOfTheGlacialAbyss("Treasure of the Glacial Abyss", Rarity.RARE),
//    ImmortalTreasureI2015("Immortal Treasure I 2015", Rarity.IMMORTAL),
//    HerosHeirloom("Hero's Heirloom"),
    ImmortalTreasureI2016("Immortal Treasure I 2016", Rarity.IMMORTAL),
    ImmortalTreasureII2015("Immortal Treasure II 2015", Rarity.IMMORTAL),
    ImmortalTreasureII2016("Immortal Treasure II 2016", Rarity.IMMORTAL),
    ImmortalTreasureIII2015("Immortal Treasure III 2015", Rarity.IMMORTAL);
//    TheInternational2015CollectorsCache("The International 2015 Collector's Cache"),
//    TreasureOfCrystallineChaos("Treasure of Crystalline Chaos", Rarity.RARE),
//    TreasureOfEmberEssence("Treasure of Ember Essence", Rarity.COMMON),
//    GoldenTroveCask("Golden Trove Cask", Rarity.IMMORTAL),
//    GoldenTroveCarafe2015("Golden Trove Carafe 2015", Rarity.IMMORTAL);

    //    private val arcanaValues: Array<Arcana> = values()
    private val random = Random()

    override fun getRandomItem(): Item {
        val treasureValues: Array<Treasure> = values()
        val randNum = random.nextInt(treasureValues.size)
        logInf(MAIN_LOGGER_TAG, "Arcana getting random: ${treasureValues[randNum].itemName}")
        return treasureValues[randNum]
    }

    override fun getName() = this.name

    override fun getItemName(): String = itemName

    override fun getCost(): Float = 0f

    override fun getRarity(): String = rarity.toString()
}