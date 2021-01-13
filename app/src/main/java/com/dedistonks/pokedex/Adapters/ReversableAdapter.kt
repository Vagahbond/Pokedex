package com.dedistonks.pokedex.Adapters

interface ReversableAdapter <I, T> {
    fun adapt(source: I): T
    fun reverseAdapt(source: T): I
}