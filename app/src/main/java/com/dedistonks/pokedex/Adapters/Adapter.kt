package com.dedistonks.pokedex.Adapters

interface Adapter<I, T> {
    fun adapt(source: I): T
}