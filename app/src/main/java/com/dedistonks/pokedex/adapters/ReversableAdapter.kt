package com.dedistonks.pokedex.adapters

interface ReversableAdapter <I, T> : Adapter<I, T>{
    fun reverseAdapt(source: T): I
}