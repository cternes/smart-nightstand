package de.slackspace.smartnightstand.device;

import java.util.Random;

import de.slackspace.smartnightstand.device.api.TempSensor;

public class DummyTempSensor implements TempSensor {

    private Random rand = new Random();
    
    @Override
    public double getTemperature() {
        return 23.5 + rand.nextDouble();
    }

}
