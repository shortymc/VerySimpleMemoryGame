package com.andraganoid.memory_kotlin

class Result( val bestMoves: Int, val bestTime: Long) {

    var currentMoves: Int = 0
    var currentTime: Long=0;

//    val bestTimeInSeconds: String
//        get() = (bestTime.toInt() / 1000).toString()
//
//    val currentTimeInSeconds: String
//        get() = (currentTime.toInt() / 1000).toString()

//    init {
//        this.currentMoves = cm
//    }
//
//    fun setCurrentTime(currentTime: Long) {
//        this.currentTime = currentTime
//    }

    fun bestTimeInSeconds(): String
    {return (bestTime.toInt() / 1000).toString()}

    fun currentTimeInSeconds(): String
    {return(currentTime.toInt() / 1000).toString()}

    fun increaseCurrentMoves() {
        currentMoves++
    }
}