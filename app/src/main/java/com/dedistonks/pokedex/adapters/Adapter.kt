package com.dedistonks.pokedex.adapters

interface Adapter<I, T> {
    fun adapt(source: I): T
}