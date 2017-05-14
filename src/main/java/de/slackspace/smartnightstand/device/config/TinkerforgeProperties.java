package de.slackspace.smartnightstand.device.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tf")
public class TinkerforgeProperties {

    private String host = "localhost";
    
    private int port = 4223;
    
    @Value("${tf.ledstrip.uid}")
    private String ledStripUid;

    @Value("${tf.temp.uid}")
    private String tempUid;
    
    private boolean useMocks;
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLedStripUid() {
        return ledStripUid;
    }

    public void setLedStripUid(String ledStripUid) {
        this.ledStripUid = ledStripUid;
    }

    public String getTempUid() {
        return tempUid;
    }

    public void setTempUid(String tempUid) {
        this.tempUid = tempUid;
    }

    public boolean isUseMocks() {
        return useMocks;
    }

    public void setUseMocks(boolean useMocks) {
        this.useMocks = useMocks;
    }
}
