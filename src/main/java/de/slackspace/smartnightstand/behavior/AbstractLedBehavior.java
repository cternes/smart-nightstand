package de.slackspace.smartnightstand.behavior;


import java.awt.Color;

import de.slackspace.smartnightstand.device.api.LedStrip;

public abstract class AbstractLedBehavior {

    protected LedStrip ledStrip;
    protected short red;
    protected short green;
    protected short blue;

    public AbstractLedBehavior(LedStrip ledStrip, int frameRatePerSecond) {
        this(ledStrip, "#000000", frameRatePerSecond);
    }

    public AbstractLedBehavior(LedStrip ledStrip, String colorHexTriplet, int frameRatePerSecond) {
        this.ledStrip = ledStrip;

        Color color = Color.decode(colorHexTriplet);
        red = (short) color.getRed();
        green = (short) color.getGreen();
        blue = (short) color.getBlue();
        ledStrip.setFrameDuration(1000 / frameRatePerSecond);
    }

}
