package com.andraganoid.memory_java;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Result extends BaseObservable {

    private int currentMoves;
    private int bestMoves;
    private long currentTime;
    private long bestTime;
    private int solved;

    Result(int bm, long bt) {
        this.bestMoves = bm;
        this.bestTime = bt;
        this.currentMoves = 0;
        this.currentTime = 0;
        this.solved = 0;
    }

    void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
        notifyPropertyChanged(BR.currentTime);
    }

    void setBestMoves(int bestMoves) {
        this.bestMoves = bestMoves;
        notifyPropertyChanged(BR.bestMoves);
    }

    @Bindable
    public int getCurrentMoves() {
        return currentMoves;
    }

    @Bindable
    public int getBestMoves() {
        return bestMoves;
    }

    @Bindable
    public long getCurrentTime() {
        return currentTime;
    }

    @Bindable
    public long getBestTime() {
        return bestTime;
    }


    public void increaseCurrentMoves() {
        currentMoves++;
        notifyPropertyChanged(BR.currentMoves);
    }

    public int getSolved() {
        return solved;
    }

    public void increaseSolved() {
        solved++;
    }

    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
        notifyPropertyChanged(BR.bestTime);
    }

}