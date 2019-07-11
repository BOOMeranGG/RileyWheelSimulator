package com.orange_infinity.rileywheelsimulator.util

class ImmutableList<T>(private val inner:List<T>) : List<T> by inner

fun <T> List<T>.toImmutableList(): List<T> {
    return if (this is ImmutableList<T>) {
        this
    } else {
        ImmutableList(this)
    }
}

class ImmutableMap<K, V>(private val inner: Map<K, V>) : Map<K, V> by inner

fun <K, V> Map<K, V>.toImmutableMap(): Map<K, V> {
    return if (this is ImmutableMap<K, V>) {
        this
    } else {
        ImmutableMap(this)
    }
}