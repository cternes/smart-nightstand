package de.slackspace.smartnightstand.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.smartnightstand.device.api.TempSensor;

public class EnhancedTempSensor implements TempSensor {

    private Log logger = LogFactory.getLog(getClass());
    
    private BrickletTemperature tempSensor;

    public EnhancedTempSensor(BrickletTemperature tempSensor) {
        this.tempSensor = tempSensor;
    }
    
    @Override
    public double getTemperature() {
        try {
            return tempSensor.getTemperature() / 100.0;
        }
        catch (TimeoutException | NotConnectedException e) {
            logger.error("Could not get temperature. Error was:", e);
        }
        return 0;
    }

}
