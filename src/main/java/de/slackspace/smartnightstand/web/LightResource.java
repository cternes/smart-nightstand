package de.slackspace.smartnightstand.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.slackspace.smartnightstand.device.Led;
import de.slackspace.smartnightstand.device.api.LedStrip;

@RestController
@RequestMapping("/api/v1/lights")
public class LightResource {

    private LedStrip ledStrip;

    @Autowired
    public LightResource(LedStrip ledStrip) {
        this.ledStrip = ledStrip; 
    }
    
    @RequestMapping(method = RequestMethod.GET, value="/current")
    public List<Led> getCurrentLedConfig() {
        return ledStrip.getCurrentLedConfig();
    }
}
