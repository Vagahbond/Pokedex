package com.dedistonks.pokedex.ui.evolutions_list


fun interface EvolutionIDConsumer {
    fun accept(id: Int): Unit
}