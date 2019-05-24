package com.andraganoid.memory_kotlin

class Result(val bestMoves: Int, val bestTime: Long) {

    var currentMoves: Int = 0
    var currentTime: Long = 0;
    var solved: Int = 0

    fun bestTimeInSeconds(): String=(bestTime.toInt() / 1000).toString()


    fun currentTimeInSeconds(): String= (currentTime.toInt() / 1000).toString()


    fun increaseCurrentMoves() {
        currentMoves++
    }

    fun increaseSolved() {
        solved++;
    }
}