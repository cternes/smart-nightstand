package de.slackspace.smartnightstand.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.smartnightstand.behavior.RainbowMode;
import de.slackspace.smartnightstand.behavior.ScanMode;
import de.slackspace.smartnightstand.device.Led;
import de.slackspace.smartnightstand.device.api.LedStrip;
import de.slackspace.smartnightstand.web.dto.LightModel;

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
    public LightModel isModeOff() {
        return new LightModel(activeMode == 0);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/current")
    public List<Led> getCurrentLedConfig() {
        return ledStrip.getCurrentLedConfig();
    }

    /**
     * Night mode: low red light
     */
    @RequestMapping(method = RequestMethod.POST, value = "/mode1")
    public void enableModeOne(@RequestBody LightModel model) {
    	if(!model.isActive()) {
    		shutdownStrip();
    		return;
    	}
    	
    	ledStrip.removeFrameRenderedListener(currentMode);
    	ledStrip.setRangeLeds(0, NUM_LEDS, "#200000");
    	activeMode = 1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mode1")
    public LightModel isModeOneEnabled() {
        return new LightModel(activeMode == 1);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mode2")
    public void enableModeTwo(@RequestBody LightModel model) {
    	if(!model.isActive()) {
    		shutdownStrip();
    		return;
    	}
    	
    	setMode(2, new ScanMode(ledStrip, 50, 0, NUM_LEDS, 20));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mode2")
    public LightModel isModeTwoEnabled() {
        return new LightModel(activeMode == 2);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mode3")
    public void enableModeThree(@RequestBody LightModel model) {
    	if(!model.isActive()) {
    		shutdownStrip();
    		return;
    	}
    	
    	setMode(3, new RainbowMode(ledStrip, 1));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mode3")
    public LightModel isModeThreeEnabled() {
        return new LightModel(activeMode == 3);
    }

    private void setMode(int mode, FrameRenderedListener newMode) {
        ledStrip.removeFrameRenderedListener(currentMode);
        ledStrip.addFrameRenderedListener(newMode);

        currentMode = newMode;
        currentMode.frameRendered(0);
        activeMode = mode;
    }
}
