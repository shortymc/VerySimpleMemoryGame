package com.andraganoid.memory_java;

import android.os.Handler;
import android.os.SystemClock;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame {

    MutableLiveData<List<Field>> memoryList;
    MutableLiveData<Result> memoryResult;

    List<Field> mList = new ArrayList<>();
    List<Integer> randFields = new ArrayList<>();
    final int FIELD_ROWS = 4;
    final int FIELD_COLS = 4;
    final int FIELDS_NUMBER = 16;
    final static int ALL_SOLVED = 8;

    int opened1, opened2, opened;

    Handler handler;
    long startTime, currentTime;


    public MemoryGame() {
        memoryList = new MutableLiveData<>();
        memoryResult = new MutableLiveData<>();
        handler = new Handler();
    }


    public void initFields(int bestMoves, long bestTime) {
        opened1 = -1;
        opened2 = -1;
        opened = 0;
        System.out.println("INIT");
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
                mList.add(new Field(randFields.get(index), index));
            }
        }

        memoryList.setValue(mList);
        memoryResult.setValue(new Result(bestMoves, bestTime));
        currentTime = 0;
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(stopwatch, 0);
    }

    int whatIsOpen(int id) {

        if (!memoryList.getValue().get(id).isSolved() && !memoryList.getValue().get(id).isOpen()) {
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

    void closeOpened() {
        opened = 0;
        memoryList.getValue().get(opened1).setOpen(false);
        memoryList.getValue().get(opened2).setOpen(false);
        opened1 = -1;
        opened2 = -1;
        memoryList.setValue(memoryList.getValue());
    }


    public int checkIsSolved() {
        memoryResult.getValue().increaseCurrentMoves();
        if (memoryList.getValue().get(opened1).getItem() == memoryList.getValue().get(opened2).getItem()) {
            memoryList.getValue().get(opened1).setSolved(true);
            memoryList.getValue().get(opened2).setSolved(true);
            memoryList.setValue(memoryList.getValue());
            memoryResult.getValue().increaseSolved();
        }
        memoryResult.setValue(memoryResult.getValue());
        return memoryResult.getValue().getSolved();

    }


    private Runnable stopwatch = new Runnable() {
        public void run() {
            currentTime = SystemClock.uptimeMillis() - startTime;
            memoryResult.getValue().setCurrentTime(currentTime);
            memoryResult.setValue(memoryResult.getValue());
            handler.postDelayed(this, 0);
        }
    };

    void stopStopwatch() {
        handler.removeCallbacks(stopwatch);
    }
}
