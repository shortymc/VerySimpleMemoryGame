package com.andraganoid.memory_kotlin

import android.os.Handler
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import java.util.*


class MemoryGame {

    internal var memoryList: MutableLiveData<List<Field>>
    internal lateinit var memoryResult: MutableLiveData<Result>

    internal var mList: MutableList<Field> = ArrayList()
    internal var randFields: MutableList<Int> = ArrayList()
    internal val FIELD_ROWS = 4
    internal val FIELD_COLS = 4
    internal val FIELDS_NUMBER = 16
    val ALL_SOLVED = 8

    internal var opened1: Int = 0
    internal var opened2: Int = 0
    internal var opened: Int = 0

    internal lateinit var handler: Handler
    internal var startTime: Long = 0
    internal var currentTime: Long = 0

    private val stopwatch = object : Runnable {
        override fun run() {
            currentTime = SystemClock.uptimeMillis() - startTime
            memoryResult.getValue()!!.currentTime = currentTime
            memoryResult.setValue(memoryResult.getValue())
            handler.postDelayed(this, 0)
        }
    }

    init {
        memoryList = MutableLiveData()
        memoryResult = MutableLiveData()
        handler = Handler()
    }


    fun initFields(bestMoves: Int, bestTime: Long) {
        opened1 = -1
        opened2 = -1
        opened = 0
        println("INIT")
        randFields.clear()
        for (i in 0 until FIELDS_NUMBER / 2) {
            randFields.add(i)
            randFields.add(i)
        }

        Collections.shuffle(randFields)
        Collections.shuffle(randFields)
        Collections.shuffle(randFields)

        mList.clear()
        var index: Int
        for (i in 0 until FIELD_ROWS) {
            for (j in 0 until FIELD_COLS) {
                index = i * FIELD_ROWS + j
                mList.add(Field(randFields[index], index))
            }
        }

        memoryList.setValue(mList)
        memoryResult.setValue(Result(bestMoves, bestTime))
        currentTime = 0
        startTime = SystemClock.uptimeMillis()
        handler.postDelayed(stopwatch, 0)
    }

    internal fun whatIsOpen(id: Int): Int {

        if (!memoryList.value!![id].isSolved && !memoryList.value!![id].isOpen) {
            opened = if (opened == 1) 2 else 1
            when (opened) {
                1 -> {
                    opened1 = id
                    memoryList.value!![opened1].isOpen = true
                    opened2 = -1
                }
                2 -> {
                    opened2 = id
                    memoryList.value!![opened2].isOpen = true
                }
            }
            memoryList.setValue(memoryList.value)
        }
        return opened
    }

    internal fun closeOpened() {
        opened = 0
        memoryList.value!![opened1].isOpen = false
        memoryList.value!![opened2].isOpen = false
        opened1 = -1
        opened2 = -1
        memoryList.setValue(memoryList.value)
    }

    fun checkIsSolved(): Int {
        memoryResult.getValue()!!.increaseCurrentMoves()
        if (memoryList.value!![opened1].item == memoryList.getValue()!!.get(opened2).item) {
            memoryList.value!![opened1].isSolved = true
            memoryList.value!![opened2].isSolved = true
            memoryList.setValue(memoryList.value)
            memoryResult.value!!.increaseSolved()
        }
        memoryResult.setValue(memoryResult.value)
        return memoryResult.value!!.solved
    }


    internal fun stopStopwatch() {
        handler.removeCallbacks(stopwatch)
    }
}
