package com.orange_infinity.rileywheelsimulator.util

/**
 * "CrestOfTheWyrmLords" -----> "Crest of the Wyrm Lords"
 *  "NyxAssassinsDagon"  -----> "Nyx Assassin's Dagon"
 */
fun convertFromCamelCase(str: String): String {
    val newStr = StringBuilder()
    newStr.append(str[0])

    for (i in 1 until str.length) {
        if (str[i].isUpperCase()) {
            if (str[i - 1] == 's') {
                newStr.insert(i, "'")
            }
            newStr.append(" ")
        }
        newStr.append(str[i])
    }
    return newStr.toString().replace(" Of ", " of ").replace(" The ", " the ")
}
