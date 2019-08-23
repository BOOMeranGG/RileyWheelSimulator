package com.orange_infinity.rileywheelsimulator.entities_layer.items

import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes
import com.orange_infinity.rileywheelsimulator.entities_layer.Heroes.*
import com.orange_infinity.rileywheelsimulator.entities_layer.items.Rarity.*
import com.orange_infinity.rileywheelsimulator.util.MAIN_LOGGER_TAG
import com.orange_infinity.rileywheelsimulator.util.logInf
import java.util.*

enum class SetItem(private val itemName: String, private val cost: Float, val hero: Heroes, val rarity: Rarity): Item {
    AcidHydra("Acid Hydra", 5f, Venomancer, MYTHICAL),
    AdornmentsOfBlight("Adornments of Blight", 5f, QueenOfPain, Rarity.RARE),
    AgaricFlourish("Agaric Flourish", 5f, TreantProtector, Rarity.RARE),
    AlphaPredator("Alpha Predator", 5f, NyxAssasin, Rarity.RARE),
    AncestorsPride("Ancestors' Pride", 5f, PhantomLancer, MYTHICAL),
    Bladesrunner("Bladesrunner", 5f, Juggernaut, Rarity.RARE),
    BlazeArmor("Blaze Armor", 5f, EmberSpirit, Rarity.RARE),
    BlessingsOfLucentyr("Blessings of Lucentyr", 5f, Luna, LEGENDARY),
    BloodyRipper("Bloody Ripper", 5f, Lifestealer, Rarity.RARE),
    BindingsOfFrost("Bindings of Frost", 5f, Morphling, UNCOMMON),
    CadenzaMagicMaster("Cadenza Magic Master", 5f, Invoker, MYTHICAL),
    CarapaceOfTheHiddenHive("Carapace of the Hidden Hive", 5f, NyxAssasin, Rarity.RARE),
    ChainedMistress("Chained Mistress", 5f, QueenOfPain, Rarity.RARE),
    ChainedSlayers("Chained Slayers", 5f, Pudge, Rarity.RARE),
    CicatrixRegalia("Cicatrix Regalia", 5f, NyxAssasin, MYTHICAL),
    CloudForgedBattleGear("Cloud Forged Battle Gear", 5f, SkywrathMage, Rarity.RARE),
    CompendiumArmsOfTheOnyxCrucible("Compendium Arms of the Onyx Crucible", 5f, LegionCommander, Rarity.RARE),
    Crescent("Crescent", 5f, Mirana, MYTHICAL),
    CompendiumUmbraRider("Compendium Umbra Rider", 5f, Luna, Rarity.RARE),
    DeathCharge("Death Charge", 5f, SpiritBreaker, MYTHICAL),
    DeadWinter("Dead Winter", 5f, Lich, MYTHICAL),
    DragonsAscension("Dragon's Ascension", 5f, DragonKnight, Rarity.RARE),
    Dragonfire("Dragonfire", 5f, Lina, Rarity.RARE),
    Dragonterror("Dragonterror", 5f, PhantomAssassin, Rarity.RARE),
    DreadhawkArmor("Dreadhawk Armor", 5f, VengefulSpirit, Rarity.RARE),
    EmberCrane("Ember Crane", 5f, Lina, MYTHICAL),
    EngulfingSpike("Engulfing Spike", 5f, Magnus, Rarity.RARE),
    EquineEmissary("Equine Emissary", 5f, LegionCommander, MYTHICAL),
    EternalNymph("Eternal Nymph", 5f, Puck, Rarity.RARE),
    ForestHermit("Forest Hermit", 5f, Earthshaker, Rarity.RARE),
    FrozenEmperor("Frozen Emperor", 5f, Lich, Rarity.RARE),
    FrozenFeather("Frozen Feather", 5f, CrystalMaiden, Rarity.RARE),
    FungalLord("Fungal Lord", 5f, NaturesProphet, MYTHICAL),
    //GhastlyMatriarch("Ghastly Matriarch", 5f, DeathProphet, Rarity.RARE),
    HiddenFlower("Hidden Flower", 5f, TemplarAssassin, Rarity.RARE),
    HumbleDrifter("Humble Drifter", 5f, PhantomLancer, Rarity.RARE),
    HunterOfKings("Hunter of Kings", 5f, Lycan, MYTHICAL),
    IcebornTrinity("Iceborn Trinity", 5f, NagaSiren, Rarity.RARE),
    ImmemorialEmperor("Immemorial Emperor", 5f,  Necrophos, Rarity.RARE),
    KeenMachine("Keen Machine", 5f, Sniper, Rarity.RARE)
    ;

    private val random = Random()

    override fun getRandomItem(): Item {
        val setItems: Array<SetItem> = values()
        val randNum = random.nextInt(setItems.size)
        logInf(MAIN_LOGGER_TAG, "Arcana getting random: ${setItems[randNum].itemName}")
        return setItems[randNum]
    }

    override fun getName() = this.name

    override fun getItemName(): String = itemName

    override fun getCost(): Float = cost

    override fun getRarity(): String = rarity.toString()
}