package de.slackspace.smartnightstand.device;

import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.device.api.LedStrip;

public class DummyLedStrip implements LedStrip {

    @Override
    public void turnOff() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAllLeds(String colorHexTriplet) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAllLeds(short red, short green, short blue) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSingleLed(int index, String colorHexTriplet) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSingleLed(int index, short red, short green, short blue) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLeds(List<Led> leds) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRangeLeds(int fromIndex, int size, String colorHexTriplet) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setRangeInverseLeds(int fromIndex, int size, String colorHexTriplet) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Led> prepareRangeLeds(int fromIndex, int size, String colorHexTriplet, List<Led> leds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Led> prepareRangeLeds(int fromIndex, int size, short red, short green, short blue, List<Led> leds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Led> prepareRangeInverseLeds(int fromIndex, int size, String colorHexTriplet, List<Led> leds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSize(int size) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Led> getCurrentLedConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFrameDuration(int duration) {
        // TODO Auto-generated method stub
    }

    @Override
    public void addFrameRenderedListener(FrameRenderedListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeFrameRenderedListener(FrameRenderedListener listener) {
        // TODO Auto-generated method stub

    }
}
