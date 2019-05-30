package com.andraganoid.memory_java;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryViewModel extends AndroidViewModel {

    Result memoryResult;
    private MutableLiveData <List <Field>> memoryList;
    public ObservableBoolean btnVisibility = new ObservableBoolean();

    private List <Field> mList = new ArrayList <>();
    private List <Integer> randFields = new ArrayList <>();
    private final int FIELD_ROWS = 4;
    private final int FIELD_COLS = 4;
    private final int FIELDS_NUMBER = 16;
    private final int ALL_SOLVED = 8;
    private int opened1, opened2, opened;
    private Handler handler;
    private long startTime, currentTime;
    private SharedPreferences prefs;
    private boolean canOpenField;
   public int itemHeight;

    MemoryViewModel(@NonNull Application application) {
        super(application);
        memoryList = new MutableLiveData <>();
        memoryResult = new Result(0, 0L);
        handler = new Handler();
        prefs = PreferenceManager.getDefaultSharedPreferences(application);
        canOpenField = true;
        btnVisibility.set(true);
    }

    LiveData <List <Field>> getMemoryList() {
        return memoryList;
    }

    public void newGame() {
        btnVisibility.set(false);
        opened1 = -1;
        opened2 = -1;
        opened = 0;
        randFields.clear();
        for (int i = 0; i < FIELDS_NUMBER / 2; i++) {
            randFields.add(i);
            randFields.add(i);
        }

        Collections.shuffle(randFields);
        Collections.shuffle(randFields);
        Collections.shuffle(randFields);

        mList.clear();
        int index;
        for (int i = 0; i < FIELD_ROWS; i++) {
            for (int j = 0; j < FIELD_COLS; j++) {
                index = i * FIELD_ROWS + j;
                mList.add(new Field(randFields.get(index), index, itemHeight));
            }
        }

        memoryList.setValue(mList);
        memoryResult.setBestMoves(prefs.getInt("bestMoves", 100));
        memoryResult.setBestTime(prefs.getLong("bestTime", 1000 * 1000L));
        currentTime = 0;
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(stopwatch, 0);
    }

   public void onFieldClicked(int id) {


        if (canOpenField) {

            if (whatIsOpen(id) == 2) {

                if (checkIsSolved() == ALL_SOLVED) {
                    stopStopwatch();
                    btnVisibility.set(true);
                    if (memoryResult.getCurrentMoves() < prefs.getInt("bestMoves", 100)) {
                        prefs.edit().putInt("bestMoves", memoryResult.getCurrentMoves()).apply();
                        Toast.makeText(getApplication(), getApplication().getString(R.string.new_best_moves), Toast.LENGTH_LONG).show();
                    }
                    if (currentTime < prefs.getLong("bestTime", 1000 * 1000L)) {
                        prefs.edit().putLong("bestTime", currentTime).apply();
                        Toast.makeText(getApplication(), getApplication().getString(R.string.new_best_time), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplication(), getApplication().getString(R.string.game_over), Toast.LENGTH_LONG).show();
                } else {
                    canOpenField = false;
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            canOpenField = true;
                            closeOpened();
                        }
                    }.start();
                }
            }
        }
    }


    private int whatIsOpen(int id) {

        if (!memoryList.getValue().get(id).isSolved() && !memoryList.getValue().get(id).isOpen()) {
            //  if (!field.isSolved() && !field.isOpen()) {
            opened = opened == 1 ? 2 : 1;
            switch (opened) {
                case 1:
                    opened1 = id;
                    memoryList.getValue().get(opened1).setOpen(true);
                    opened2 = -1;
                    break;
                case 2:
                    opened2 = id;
                    memoryList.getValue().get(opened2).setOpen(true);
                    break;
            }
            memoryList.setValue(memoryList.getValue());
        }
        return opened;
    }

    private void closeOpened() {
        opened = 0;
        memoryList.getValue().get(opened1).setOpen(false);
        memoryList.getValue().get(opened2).setOpen(false);
        opened1 = -1;
        opened2 = -1;
        memoryList.setValue(memoryList.getValue());
    }


    private int checkIsSolved() {
        memoryResult.increaseCurrentMoves();
        if (memoryList.getValue().get(opened1).getItem() == memoryList.getValue().get(opened2).getItem()) {
            memoryList.getValue().get(opened1).setSolved(true);
            memoryList.getValue().get(opened2).setSolved(true);
            memoryList.setValue(memoryList.getValue());
            memoryResult.increaseSolved();
        }
        return memoryResult.getSolved();

    }


    private Runnable stopwatch = new Runnable() {
        public void run() {
            currentTime = SystemClock.uptimeMillis() - startTime;
            memoryResult.setCurrentTime(currentTime);
            handler.postDelayed(this, 0);
        }
    };

    void stopStopwatch() {
        handler.removeCallbacks(stopwatch);
    }


}
