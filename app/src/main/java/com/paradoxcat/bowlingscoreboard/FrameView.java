package com.paradoxcat.bowlingscoreboard;

public class FrameView {

    private String total;
    private String pins;

    public  FrameView(String total, String pins) {
        this.total = total;
        this.pins = pins;
    }

    public String getTotal() {
        return total;
    }

    public String getPins() {
        return pins;
    }

    @Override
    public String toString() {
        return total + " " + pins;
    }
}
