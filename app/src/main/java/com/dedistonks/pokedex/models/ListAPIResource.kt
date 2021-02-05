package com.dedistonks.pokedex.models


abstract class ListAPIResource(var contentType: ListContentType) {
    abstract val name: String
    abstract val id: Int

    abstract fun isComplete() : Boolean

}

