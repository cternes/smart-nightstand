package de.slackspace.smartnightstand.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.behavior.RainbowMode;
import de.slackspace.smartnightstand.behavior.ScanMode;
import de.slackspace.smartnightstand.device.Led;
import de.slackspace.smartnightstand.device.api.LedStrip;
import de.slackspace.smartnightstand.web.dto.LightResponse;

@RestController
@RequestMapping("/api/v1/lights")
public class LightResource {

    private static final int NUM_LEDS = 42;
    private LedStrip ledStrip;
    private FrameRenderedListener currentMode;
    private int activeMode = 0;

    @Autowired
    public LightResource(LedStrip ledStrip) {
        this.ledStrip = ledStrip;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/off")
    public void shutdownStrip() {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.turnOff();
        activeMode = 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/off")
    public LightResponse isModeOff() {
        return new LightResponse(activeMode == 0);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/current")
    public List<Led> getCurrentLedConfig() {
        return ledStrip.getCurrentLedConfig();
    }

    /**
     * Night mode: low red light
     */
    @RequestMapping(method = RequestMethod.POST, value = "/mode1")
    public void enableModeOne() {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.setRangeLeds(0, NUM_LEDS, "#200000");
        activeMode = 1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mode1")
    public LightResponse isModeOneEnabled() {
        return new LightResponse(activeMode == 1);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mode2")
    public void enableModeTwo() {
        setMode(2, new ScanMode(ledStrip, 50, 0, NUM_LEDS, 20));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mode2")
    public LightResponse isModeTwoEnabled() {
        return new LightResponse(activeMode == 2);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mode3")
    public void enableModeThree() {
        setMode(3, new RainbowMode(ledStrip, 1));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mode3")
    public LightResponse isModeThreeEnabled() {
        return new LightResponse(activeMode == 3);
    }

    private void setMode(int mode, FrameRenderedListener newMode) {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.addFrameRenderedListener(newMode);

        currentMode = newMode;
        currentMode.frameRendered(0);
        activeMode = mode;
    }

}
