package de.slackspace.smartnightstand.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickMaster;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NetworkException;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.smartnightstand.device.config.TinkerforgeProperties;

@Service
public class MasterService {

    private Log logger = LogFactory.getLog(getClass());
    private IPConnection ipcon = new IPConnection();

    private TinkerforgeProperties properties;

    @Autowired
    public MasterService(TinkerforgeProperties properties) {
        this.properties = properties;
        disableStatusLed();
    }

    private void disableStatusLed() {
        try {
            ipcon.connect(properties.getHost(), properties.getPort());
            BrickMaster master = new BrickMaster(properties.getMasterUid(), ipcon);
            master.disableStatusLED();
        } catch (NetworkException | AlreadyConnectedException | NotConnectedException e) {
            logger.error("Could not connect to led strip", e);
        } catch (TimeoutException e) {
            logger.error("Timeout while sending command to master", e);
        }
    }

}
