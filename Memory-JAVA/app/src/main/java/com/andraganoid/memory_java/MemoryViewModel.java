package com.andraganoid.memory_java;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import static com.andraganoid.memory_java.MainActivity.newGameBtn;

public class MemoryViewModel extends AndroidViewModel {

    MemoryGame mGame;
    SharedPreferences prefs;
    boolean canOpenField;


    public MemoryViewModel(@NonNull Application application) {
        super(application);
        mGame = new MemoryGame();
        prefs = PreferenceManager.getDefaultSharedPreferences(application);
        canOpenField = true;
    }

    public LiveData <List <Field>> getMemoryList() {
        return mGame.memoryList;
    }

    public LiveData <Result> getMemoryResults() {
        return mGame.memoryResult;
    }

    void newGame() {
        mGame.initFields(prefs.getInt("bestMoves", 100), prefs.getLong("bestTime", 1000 * 1000L));
    }

    public void fieldClicked(int id) {

        if (canOpenField) {

            if (mGame.whatIsOpen(id) == 2) {

                if (mGame.checkIsSolved()) {
                    if (mGame.ifAllSolved()) {
                        mGame.stopStopwatch();
                        if (mGame.memoryResult.getValue().getCurrentMoves() < prefs.getInt("bestMoves", 100)) {
                            prefs.edit().putInt("bestMoves", mGame.memoryResult.getValue().getCurrentMoves()).commit();
                            Toast.makeText(getApplication(), "NEW BEST MOVES!", Toast.LENGTH_LONG).show();
                        }
                        if (mGame.currentTime < prefs.getLong("bestTime", 1000 * 1000L)) {
                            prefs.edit().putLong("bestTime", mGame.currentTime).commit();
                            Toast.makeText(getApplication(), "NEW BEST TIME!", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplication(), "GAME OVER", Toast.LENGTH_LONG).show();
                        newGameBtn.setVisibility(View.VISIBLE);

                    }
                } else {
                    canOpenField = false;
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            canOpenField = true;
                            mGame.closeOpened();
                        }
                }.start();
                }
            }
        }
    }

    void stopTimer() {
        mGame.stopStopwatch();
    }
}
