package de.slackspace.smartnightstand.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.smartnightstand.device.api.LedStrip;
import de.slackspace.smartnightstand.device.config.TinkerforgeProperties;

@Component
public class LedStripProvider {

	private Log logger = LogFactory.getLog(getClass());
	
    private IPConnection ipcon = new IPConnection();
    private LedStrip ledStrip;

    private TinkerforgeProperties properties;
    
    @Autowired
    public LedStripProvider(TinkerforgeProperties properties) {
        this.properties = properties;
    }
    
    @Bean
    public LedStrip createLedStrip() {
        if (properties.isUseMocks()) {
            return new DummyLedStrip();
        }
        
        BrickletLEDStrip strip = new BrickletLEDStrip(properties.getLedStripUid(), ipcon);
        try {
			ipcon.connect(properties.getHost(), properties.getPort());
			strip.setChipType(BrickletLEDStrip.CHIP_TYPE_WS2812);
			
			return new EnhancedLedStrip(strip);
		} catch (AlreadyConnectedException | TimeoutException | NotConnectedException | NetworkException e) {
			logger.error("Could not connect to led strip", e);
			
			return null;
		}
    }
    
    public void disconnect() {
    	try {
    		ledStrip.turnOff();
    		
    		if (!properties.isUseMocks()) {
    		    ipcon.disconnect();
    		}
		} catch (NotConnectedException e) {
			logger.error("Could not disconnect to led strip", e);
		}
    }
}
