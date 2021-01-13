package com.dedistonks.pokedex.Adapters

interface ReversableAdapter <I, T> : Adapter<I, T>{
    fun reverseAdapt(source: T): I
}