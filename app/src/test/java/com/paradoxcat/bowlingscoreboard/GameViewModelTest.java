package com.paradoxcat.bowlingscoreboard;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class GameViewModelTest {
    private GameViewModel viewModel;
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        viewModel = new GameViewModel();
    }

    @Test
    public void shouldConvertRollsToFrameView() {

        viewModel.onNextClicked(3);
        viewModel.onNextClicked(5);

        List<FrameView> list = Collections.singletonList(new FrameView("8", "3  5"));
        assertEquals(list.get(0).toString(), viewModel.getLiveFramesToView().getValue().get(0).toString());
    }

    @Test
    public void shouldRenderNumbersCorrect() {

        viewModel.onNextClicked(3);
        assertEquals(8, viewModel.getNextAcceptedNums().getValue().size());
    }

    @Test
    public void GameShouldBeEndedFullStrike() {

        roll(12, 10);
        assertEquals(true, viewModel.getIsGameEnded().getValue());
    }

    @Test
    public void GameShouldBeEnded() {

        roll(20, 8);
        assertEquals(true, viewModel.getIsGameEnded().getValue());
    }

    private void roll(int times, int pins) {
        for (int i = 0; i < times; i++) {
            viewModel.onNextClicked(pins);
        }
    }
}