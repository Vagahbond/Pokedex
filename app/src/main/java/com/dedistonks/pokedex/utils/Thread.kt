package com.dedistonks.pokedex.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Thread {
    companion object {
        @JvmStatic
        fun thread(callback: () -> Unit) {
            GlobalScope.launch(Dispatchers.IO) { callback() }
        }
    }
}