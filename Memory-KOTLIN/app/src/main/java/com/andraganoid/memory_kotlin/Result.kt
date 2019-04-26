package com.andraganoid.memory_kotlin

class Result(var bestMoves: Int, var bestTime: Long) {
    var currentMoves = 0;
    var currentTime = 0;
    
    val bestTimeInSeconds: String
        get() = (bestTime.toInt() / 1000).toString()

    val currentTimeInSeconds: String
        get() = (currentTime.toInt() / 1000).toString()

    fun increaseCurrentMoves() {
        currentMoves++
    }
}