package com.paradoxcat.bowlingscoreboard;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameViewModel extends ViewModel {
    private MutableLiveData<String> totalScore = new MutableLiveData<>();
    private MutableLiveData<List<FrameView>> liveFramesToView = new MutableLiveData<>();
    private MutableLiveData<Boolean> isGameEnded = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> nextAcceptedNums = new MutableLiveData<>();

    private Game game = new Game();


    public GameViewModel() {
        nextAcceptedNums.setValue(getAcceptedNums(11));
    }

    public void onNextClicked(int numOfPins) {

        game.roll(numOfPins);
        List<Frame> frames = game.getFrames();
        totalScore.setValue(" " + intToString(game.getScore()));


        enhanceFramesToView(frames);
        isGameEnded.setValue(game.isGameEnded());

    }

    private void enhanceFramesToView(List<Frame> frames) {
        List<FrameView> framesToView = new ArrayList<>();
        for (Frame frame : frames) {
            String scoreToView = String.valueOf(frame.getScore());
            switch (frame.getStatus()) {
                case isStrike:
                case doubleStrike:
                    framesToView.add(new FrameView(scoreToView, "X"));
                    break;
                case isSpare:
                    framesToView.add(new FrameView(scoreToView,
                            intToString(frame.getFirstTry()) + "  /"));
                    break;
                case onExtraShot:
                    if (frame.getScore() == 30)
                        framesToView.add(new FrameView(scoreToView, "X"));
                    else
                        framesToView.add(new FrameView(scoreToView, intToString(frame.getFirstTry()) +
                                " " + intToString(frame.getSecondTry()) + " " + intToString(frame.getExtraTry())));
                    break;
                default:
                    framesToView.add(new FrameView(scoreToView,
                            intToString(frame.getFirstTry()) + "  " +
                                    intToString(frame.getSecondTry())));
            }

        }
        liveFramesToView.setValue(framesToView);
        nextAcceptedNums.setValue(getAcceptedNums(game.nextAcceptedNumber() + 1));
    }

    private List<Integer> getAcceptedNums(Integer maxNums) {
        List<Integer> list = new ArrayList<>(maxNums);

        for (Integer i = 0; i < maxNums; i++) {
            list.add(i);
        }
        return list;
    }

    private static String intToString(int num) {
        return String.valueOf(num);
    }


    public MutableLiveData<List<FrameView>> getLiveFramesToView() {
        return liveFramesToView;
    }

    public MutableLiveData<String> getTotalScore() {
        return totalScore;
    }

    public MutableLiveData<Boolean> getIsGameEnded() {
        return isGameEnded;
    }

    public MutableLiveData<List<Integer>> getNextAcceptedNums() {
        return nextAcceptedNums;
    }

    public void onNewGameClicked() {
        game = new Game();
        liveFramesToView.setValue(new ArrayList<>());
        nextAcceptedNums.setValue(getAcceptedNums(11));
        totalScore.setValue(null);
        isGameEnded.setValue(false);
    }


}
