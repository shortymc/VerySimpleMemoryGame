package com.andraganoid.memory_java;

public class Result {

    private int currentMoves;
    private int bestMoves;
    private long currentTime;
    private long bestTime;

    public Result(int cm, int bm, long ct, long bt) {
        this.currentMoves = cm;
        this.bestMoves = bm;
        this.currentTime = ct;
        this.bestTime = bt;
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
}
