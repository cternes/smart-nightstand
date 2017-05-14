package de.slackspace.smartnightstand.device;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.NotConnectedException;

import de.slackspace.smartnightstand.device.api.TempSensor;
import de.slackspace.smartnightstand.device.config.TinkerforgeProperties;
  
@Component
public class TempSensorProvider {

    private Log logger = LogFactory.getLog(getClass());
    private IPConnection ipcon = new IPConnection();
    private TinkerforgeProperties properties;
    
    @Autowired
    public TempSensorProvider(TinkerforgeProperties properties) {
        this.properties = properties;
    }
    
    @Bean
    public TempSensor createTempSensor() {
        if (properties.isUseMocks()) {
            return new DummyTempSensor();
        }
        
        BrickletTemperature tempSensor = new BrickletTemperature(properties.getTempUid(), ipcon);
        
        try {
            ipcon.connect(properties.getHost(), properties.getPort());
            
            return new EnhancedTempSensor(tempSensor);
        } catch (AlreadyConnectedException | NetworkException e) {
            logger.error("Could not connect to temperature sensor", e);
            
            return null;
        }
    }
    
    @PreDestroy
    public void disconnect() {
        try {
            if (!properties.isUseMocks()) {
                ipcon.disconnect();
            }
        } catch (NotConnectedException e) {
            logger.error("Could not disconnect to led strip", e);
        }
    }
}
