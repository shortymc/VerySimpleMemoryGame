package com.andraganoid.memory_kotlin

import android.app.Application
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class MemoryViewModel(application: Application) : AndroidViewModel(application) {

    private var mGame: MemoryGame
    private var prefs: SharedPreferences
    private var canOpenField: Boolean = false

    val memoryList: LiveData<List<Field>>
        get() = mGame.memoryList

    val memoryResults: LiveData<Result>
        get() = mGame.memoryResult


    init {
        mGame = MemoryGame()
        prefs = PreferenceManager.getDefaultSharedPreferences(application)
        canOpenField = true
    }

    fun newGame() {
        mGame.initFields(prefs.getInt("bestMoves", 100), prefs.getLong("bestTime", 1000 * 1000L))
    }

    fun fieldClicked(id: Int) {

        if (canOpenField) {

            if (mGame.whatIsOpen(id) == 2) {

                if (mGame.checkIsSolved() == mGame.ALL_SOLVED) {

                    mGame.stopStopwatch()
                    if (mGame.memoryResult.getValue()!!.currentMoves < prefs.getInt("bestMoves", 100)) {
                        prefs.edit().putInt("bestMoves", mGame.memoryResult.getValue()!!.currentMoves).apply()
                        Toast.makeText(getApplication(), "NEW BEST MOVES!", Toast.LENGTH_LONG).show()
                    }
                    if (mGame.currentTime < prefs.getLong("bestTime", 1000 * 1000L)) {
                        prefs.edit().putLong("bestTime", mGame.currentTime).apply()
                        Toast.makeText(getApplication(), "NEW BEST TIME!", Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(getApplication(), "GAME OVER", Toast.LENGTH_LONG).show()
                } else {
                    canOpenField = false
                    object : CountDownTimer(1000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {}

                        override fun onFinish() {
                            canOpenField = true
                            mGame.closeOpened()
                        }
                    }.start()
                }
            }
        }
    }

     fun stopTimer() {
        mGame.stopStopwatch()
    }
}
