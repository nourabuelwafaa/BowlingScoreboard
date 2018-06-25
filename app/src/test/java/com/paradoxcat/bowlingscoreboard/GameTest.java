package com.paradoxcat.bowlingscoreboard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class GameTest {
    private Game game;

    @Before
    public void init() {
        game = new Game();
    }

    @Test
    public void canGetZero() {
        roll(0, 20);

        assertEquals(0, game.getScore());
    }

    @Test
    public void canScoreOne() {
        roll(1, 20);
        assertEquals(20, game.getScore());
    }

    @Test
    public void canScoreStrike() {
        game.roll(10);
        game.roll(2);
        game.roll(3);
        assertEquals(20, game.getScore());

    }

    @Test
    public void canScoreSpare() {
        game.roll(8);
        game.roll(2);
        game.roll(3);
        game.roll(3);
        assertEquals(19, game.getScore());

    }

    @Test
    public void strikeWithSpare() {
        game.roll(10);
        game.roll(2);
        game.roll(8);
        game.roll(10);
        game.roll(2);
        game.roll(4);
        assertEquals(62, game.getScore());

    }

    @Test
    public void doubleStrike() {
        game.roll(10);
        game.roll(10);
        game.roll(4);
        game.roll(2);

        assertEquals(46, game.getScore());

    }

    @Test
    public void randomTest() {
        game.roll(7);
        game.roll(3);
        game.roll(4);
        game.roll(2);

        assertEquals(20, game.getScore());

    }

    @Test
    public void gameMustEnd() {
        roll(1, 20);
        assertEquals(true, game.isGameEnded());

    }

    @Test
    public void gameIsNotEnded() {
        roll(1, 19);
        assertEquals(false, game.isGameEnded());

    }

    @Test
    public void gameMustBeEndedWithExtraShots() {
        roll(10, 12);
        assertEquals(true, game.isGameEnded());

    }

    @Test
    public void gameMustNotBeEndedWithExtraShots() {
        roll(10, 11);
        assertEquals(false, game.isGameEnded());

    }

    @Test
    public void allStrikes() {
        roll(10, 12);
        assertEquals(300, game.getScore());

    }

    @Test
    public void extraShot() {
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(1);

        assertEquals(291, game.getScore());

    }

    @Test
    public void lastFrame() {
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(8);
        game.roll(2);

        assertEquals(288, game.getScore());

    }

    @Test
    public void lastFrameWithSpare() {
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(8);
        game.roll(2);
        game.roll(10);


        assertEquals(278, game.getScore());

    }

    @Test
    public void nextAcceptedWithNormalRoll() {
        game.roll(5);

        assertEquals(5, game.nextAcceptedNumber());

    }

    @Test
    public void nextAcceptedWithStrike() {
        game.roll(10);

        assertEquals(10, game.nextAcceptedNumber());

    }

    private void roll(int pins, int time) {
        for (int i = 0; i < time; i++) {
            game.roll(pins);
        }
    }

}