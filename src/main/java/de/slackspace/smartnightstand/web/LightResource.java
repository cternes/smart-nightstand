package de.slackspace.smartnightstand.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.behavior.KnightRiderMode;
import de.slackspace.smartnightstand.behavior.RainbowMode;
import de.slackspace.smartnightstand.device.Led;
import de.slackspace.smartnightstand.device.api.LedStrip;

@RestController
@RequestMapping("/api/v1/lights")
public class LightResource {

    private static final int NUM_LEDS = 42;
    private LedStrip ledStrip;
    private FrameRenderedListener currentMode;

    @Autowired
    public LightResource(LedStrip ledStrip) {
        this.ledStrip = ledStrip;
    }

    @RequestMapping(method = RequestMethod.GET, value="/current")
    public List<Led> getCurrentLedConfig() {
        return ledStrip.getCurrentLedConfig();
    }

    @RequestMapping(method = RequestMethod.POST, value="/off")
    public void shutdownStrip() {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.turnOff();
    }

    /**
     * Night mode: low red light
     */
    @RequestMapping(method = RequestMethod.POST, value="/mode1")
    public void enableModeOne() {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.setRangeLeds(0, NUM_LEDS, "#200000");
    }

    @RequestMapping(method = RequestMethod.POST, value="/mode2")
    public void enableModeTwo() {
        setMode(new KnightRiderMode(ledStrip, 50, 900, 0, NUM_LEDS, 20));
    }

    @RequestMapping(method = RequestMethod.POST, value="/mode3")
    public void enableModeThree() {
        setMode(new RainbowMode(ledStrip, 1));
    }

    private void setMode(FrameRenderedListener newMode) {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.addFrameRenderedListener(newMode);

        currentMode = newMode;
        currentMode.frameRendered(0);
    }
}
