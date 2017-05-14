package de.slackspace.smartnightstand.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.slackspace.smartnightstand.device.api.TempSensor;

@RestController
@RequestMapping("/api/v1/temperatures")
public class TemperatureResource {

    private TempSensor sensor;

    @Autowired
    public TemperatureResource(TempSensor sensor) {
        this.sensor = sensor;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/bedroom")
    public double getTemperature() {
        return sensor.getTemperature();
    }
}
