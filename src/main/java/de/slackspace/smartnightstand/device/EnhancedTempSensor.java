package de.slackspace.smartnightstand.device;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
            BigDecimal hundred = new BigDecimal(100);
            return new BigDecimal(tempSensor.getTemperature()).divide(hundred, 1, RoundingMode.HALF_UP).doubleValue();
        }
        catch (TimeoutException | NotConnectedException e) {
            logger.error("Could not get temperature. Error was:", e);
        }
        return 0;
    }

}
