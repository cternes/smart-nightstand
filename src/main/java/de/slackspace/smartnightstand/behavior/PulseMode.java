package de.slackspace.smartnightstand.behavior;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.device.Led;
import de.slackspace.smartnightstand.device.api.LedStrip;


public class PulseMode extends AbstractLedBehavior implements FrameRenderedListener {

    private int numOfFullAnimations = 0;

    private boolean increment = true;
    private int numIncrement = 1;
    private int minBoundary = 0;
    private int maxBoundary = 150;
    private Color color;
    private int changeColor;

    public PulseMode(LedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary) {
        this(ledStrip, frameRatePerSecond, minBoundary, maxBoundary, 2);
    }

    public PulseMode(LedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary, int changeColor) {
        super(ledStrip, frameRatePerSecond);

        this.minBoundary = minBoundary;
        this.maxBoundary = maxBoundary;
        this.changeColor = changeColor;

        switch (changeColor) {
        case 1: color = new Color(1, 0, 0); break;
        case 2: color = new Color(0, 1, 0); break;
        case 3: color = new Color(0, 0, 1); break;
        default: color = new Color(1, 0, 0); break;
        }
    }

    @Override
    public void frameRendered(int length) {
        ledStrip.turnOff();
        List<Led> leds = new ArrayList<>();
        leds = ledStrip.prepareRangeLeds(minBoundary, maxBoundary - minBoundary, (short)color.getRed(), (short)color.getGreen(), (short)color.getBlue(), leds);
        ledStrip.setLeds(leds);

        calcNextColor();
    }

    private void calcNextColor() {
        if(increment) {
            brighter();
        }
        else {
            darker();
        }

        if(isMaxBright()) {
            increment = false;
        }
        else if (isMinBright()) {
            increment = true;
            numOfFullAnimations++;
        }
    }

    private boolean isMaxBright() {
        int maxBrightness = 110;
        if(color.getBlue() > maxBrightness) {
            return true;
        }
        else if(color.getRed() > maxBrightness) {
            return true;
        }
        else if(color.getGreen() > maxBrightness) {
            return true;
        }

        return false;
    }

    private boolean isMinBright() {
        if(color.getBlue() == 0 && color.getRed() == 0 && color.getGreen() == 0) {
            return true;
        }

        return false;
    }

    private void brighter() {
        if(changeColor == 1) {
            color = new Color(0, 0, color.getBlue() + numIncrement);
        }
        else if(changeColor == 2) {
            color = new Color(color.getRed() + numIncrement, 0, 0);
        }
        else if(changeColor == 3) {
            color = new Color(0, color.getGreen() + numIncrement, 0);
        }
    }

    private void darker() {
        if(color.getBlue() > 0) {
            color = new Color(0, 0, color.getBlue() - numIncrement);
        }
        else if(color.getRed() > 0) {
            color = new Color(color.getRed() - numIncrement, 0, 0);
        }
        else if(color.getGreen() > 0) {
            color = new Color(0, color.getGreen() - numIncrement, 0);
        }
    }

    public int getNumOfFullAnimations() {
        return numOfFullAnimations;
    }

    public void setNumOfFullAnimations(int numOfFullAnimations) {
        this.numOfFullAnimations = numOfFullAnimations;
    }

}
