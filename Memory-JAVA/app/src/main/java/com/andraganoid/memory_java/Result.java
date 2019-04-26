package com.andraganoid.memory_java;

public class Result {

    private int currentMoves;
    private int bestMoves;
    private long currentTime;
    private long bestTime;
    private int solved;

    public Result(int bm, long bt) {
        this.bestMoves = bm;
        this.bestTime = bt;
        this.currentMoves = 0;
        this.currentTime = 0;
        this.solved = 0;
    }

    public int getCurrentMoves() {
        return currentMoves;
    }

    public int getBestMoves() {
        return bestMoves;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public String getBestTimeInSeconds() {
        return String.valueOf((int) bestTime / 1000);
    }

    public String getCurrentTimeInSeconds() {
        return String.valueOf((int) currentTime / 1000);
    }

    public void increaseCurrentMoves() {
        currentMoves++;
    }

    public int getSolved() {
        return solved;
    }
    

    public void increaseSolved() {
        solved++;
    }
}
