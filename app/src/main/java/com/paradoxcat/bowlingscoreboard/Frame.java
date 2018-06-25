package com.paradoxcat.bowlingscoreboard;

public class Frame {

    private int firstTry;
    private int secondTry;
    private int extraTry;
    private Status status = Status.onFirstShot;
    private int score;

    public int getExtraTry() {
        return extraTry;
    }

    public void setExtraTry(int extraTry) {
        this.extraTry = extraTry;
    }

    public int getFirstTry() {
        return firstTry;
    }

    public int getSecondTry() {
        return secondTry;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setFirstTry(int firstTry) {
        this.firstTry = firstTry;
    }

    public void setSecondTry(int secondTry) {
        this.secondTry = secondTry;
    }

    enum Status {

        onFirstShot, onSecondShot, isStrike,
        doubleStrike, onLastFrameWithStrike, onExtraShot, isSpare
    }
}
