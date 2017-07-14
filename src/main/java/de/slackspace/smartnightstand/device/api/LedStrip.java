package de.slackspace.smartnightstand.device.api;

import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.device.Led;

public interface LedStrip {

    public void turnOff();

    public void setAllLeds(String colorHexTriplet);

    public void setAllLeds(short red, short green, short blue);

    public void setSingleLed(int index, String colorHexTriplet);

    public void setSingleLed(int index, short red, short green, short blue);

    public void setLeds(List<Led> leds);

    public void setRangeLeds(int fromIndex, int size, String colorHexTriplet);

    public void setRangeInverseLeds(int fromIndex, int size, String colorHexTriplet);

    public List<Led> prepareRangeLeds(int fromIndex, int size, String colorHexTriplet, List<Led> leds);

    public List<Led> prepareRangeLeds(int fromIndex, int size, short red, short green, short blue, List<Led> leds);

    public List<Led> prepareRangeInverseLeds(int fromIndex, int size, String colorHexTriplet, List<Led> leds);

    public int getSize();

    public void setSize(int size);

    public List<Led> getCurrentLedConfig();

    public void setFrameDuration(int duration);

    public void addFrameRenderedListener(FrameRenderedListener listener);

    public void removeFrameRenderedListener(FrameRenderedListener listener);
}
