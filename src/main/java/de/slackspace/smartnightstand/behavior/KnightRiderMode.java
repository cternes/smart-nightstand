package de.slackspace.smartnightstand.behavior;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.device.api.LedStrip;

public class KnightRiderMode extends AbstractLedBehavior implements FrameRenderedListener {

    private int mode = 1;

    private PulseMode pulseMode;
    private ScanMode scanMode;

    private int frameRatePerSecondScanning;
    private int frameRatePerSecondPulsing;

    public KnightRiderMode(LedStrip ledStrip, int frameRatePerSecondScanning, int frameRatePerSecondPulsing, int minBoundary, int maxBoundary, int sizeOfBrightLeds) {
        super(ledStrip, frameRatePerSecondScanning);
        this.frameRatePerSecondScanning = frameRatePerSecondScanning;
        this.frameRatePerSecondPulsing = frameRatePerSecondPulsing;

        pulseMode = new PulseMode(ledStrip, frameRatePerSecondPulsing, minBoundary, maxBoundary, 2);
        scanMode = new ScanMode(ledStrip, frameRatePerSecondScanning, minBoundary, maxBoundary, sizeOfBrightLeds);
    }

    @Override
    public void frameRendered(int length) {
        switch(mode) {
        case 1: scanMode.frameRendered(length);
        ledStrip.setFrameDuration(1000 / frameRatePerSecondScanning);
        if(scanMode.getNumOfFullAnimations() == 5) {
            scanMode.setNumOfFullAnimations(0);
            mode++;
        }
        break;
        case 2: pulseMode.frameRendered(length);
        ledStrip.setFrameDuration(1000 / frameRatePerSecondPulsing);
        if(pulseMode.getNumOfFullAnimations() == 3) {
            pulseMode.setNumOfFullAnimations(0);
            mode++;
        }
        break;
        case 3: mode = 1;
        break;
        }

    }

}
