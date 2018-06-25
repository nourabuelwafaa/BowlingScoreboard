package com.paradoxcat.bowlingscoreboard;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Frame> frames = new ArrayList<>();
    private Frame currentFrame = new Frame();
    private boolean gameEnded;

    public boolean isGameEnded() {
        return gameEnded;
    }

    public Game() {
        frames.add(currentFrame);
    }

    public int nextAcceptedNumber() {
        switch (currentFrame.getStatus()) {
            case onSecondShot:
                int i = 10 - currentFrame.getFirstTry();
                if (i == 0) return 10;
                else return i;
            default:
                return 10;
        }
    }

    private void onFirstShot(int pins, Frame lastFrame) {
        // onFirst shot and also strike
        if (isStrike(pins)) {
            //checking if last frame and isStrike
            if (isLastFrame()) {
                if (lastFrame.getStatus() == Frame.Status.doubleStrike) {
                    Frame doubleStrikeFrame = getDoubleStrikeFrame();
                    doubleStrikeFrame.setScore(20 + pins);
                    currentFrame.setStatus(Frame.Status.onLastFrameWithStrike);
                } else {
                    currentFrame.setStatus(Frame.Status.onSecondShot);
                    currentFrame.setFirstTry(10);
                }


            } else {
                // first will check if the last frame was also strike
                if (lastFrame.getStatus() == Frame.Status.isStrike) {
                    // this means its a double strike will end current frame
                    // and will first frame will be calculated next round;
                    currentFrame.setStatus(Frame.Status.doubleStrike);

                } else if (lastFrame.getStatus() == Frame.Status.doubleStrike) {
                    Frame doubleStrikeFrame = getDoubleStrikeFrame();
                    doubleStrikeFrame.setScore(20 + pins);
                    currentFrame.setStatus(Frame.Status.doubleStrike);


                } else {
                    currentFrame.setStatus(Frame.Status.isStrike);

                }
                if (lastFrame.getStatus() == Frame.Status.isSpare) {

                    lastFrame.setScore(10 + pins);

                }
                // On first shot and also strike will end the current frame
                frames.add(currentFrame = new Frame());

            }
        } else {

            if (lastFrame.getStatus() == Frame.Status.doubleStrike) {
                Frame doubleStrikeFrame = getDoubleStrikeFrame();
                doubleStrikeFrame.setScore(20 + pins);
                lastFrame.setStatus(Frame.Status.isStrike);

            }
            currentFrame.setFirstTry(pins);
            currentFrame.setStatus(Frame.Status.onSecondShot);
        }
        if (lastFrame.getStatus() == Frame.Status.isSpare) {
            lastFrame.setScore(10 + pins);
        }
    }

    private void onSecondShot(int pins, Frame lastFrame) {
        if (lastFrame.getStatus() == Frame.Status.isStrike) {
            lastFrame.setScore(10 + currentFrame.getFirstTry() + pins);
        }
        // if is spare will be calculated next frame on first try
        if (isSpare(pins + currentFrame.getFirstTry())) {
            if (isLastFrame()) {
                currentFrame.setSecondTry(pins);
                currentFrame.setStatus(Frame.Status.onExtraShot);

            } else
                currentFrame.setStatus(Frame.Status.isSpare);
        } else {
            currentFrame.setSecondTry(pins);
            currentFrame.setScore(currentFrame.getFirstTry() + pins);
        }
        if (isLastFrame()) {
            if (currentFrame.getFirstTry() == 10)
                currentFrame.setStatus(Frame.Status.onExtraShot);
            if (currentFrame.getStatus() != Frame.Status.onExtraShot)
                gameEnded = true;
        } else frames.add(currentFrame = new Frame());
    }


    void roll(int pins) {

        if (gameEnded) return;
        Frame lastFrame = getLastFrame();

        if (currentFrame.getStatus() == Frame.Status.onFirstShot) {
            onFirstShot(pins, lastFrame);
        } else if (currentFrame.getStatus() == Frame.Status.onSecondShot) {
            onSecondShot(pins, lastFrame);
        } else if (currentFrame.getStatus() == Frame.Status.onLastFrameWithStrike) {
            lastFrame.setScore(20 + pins);
            currentFrame.setFirstTry(10);
            currentFrame.setSecondTry(pins);
            currentFrame.setStatus(Frame.Status.onExtraShot);

        } else if (currentFrame.getStatus() == Frame.Status.onExtraShot) {
            currentFrame.setExtraTry(pins);
            currentFrame.setScore(currentFrame.getFirstTry() + currentFrame.getSecondTry() + pins);
            gameEnded = true;

        }

    }

    private boolean isLastFrame() {
        return frames.size() == 10;
    }

    private Frame getLastFrame() {
        if (frames.size() > 1)
            return frames.get(frames.size() - 2);
        else return frames.get(frames.size() - 1);
    }

    private Frame getDoubleStrikeFrame() {
        return frames.get(frames.size() - 3);
    }

    private boolean isStrike(int pins) {
        return pins == 10;
    }

    private boolean isSpare(int pins) {
        return pins == 10;
    }


    int getScore() {
        int score = 0;
        for (Frame frame : frames) {
            score += frame.getScore();
        }
        return score;
    }

    public List<Frame> getFrames() {
        return frames;
    }
}
