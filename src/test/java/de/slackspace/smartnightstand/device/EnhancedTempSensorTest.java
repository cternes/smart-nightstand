package de.slackspace.smartnightstand.device;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

@RunWith(SpringRunner.class)
public class EnhancedTempSensorTest {

    @MockBean
    private BrickletTemperature bricklet;

    @Test
    public void getTemperature() throws TimeoutException, NotConnectedException {
        when(bricklet.getTemperature()).thenReturn((short) 2345);

        EnhancedTempSensor sensor = new EnhancedTempSensor(bricklet);

        assertThat(sensor.getTemperature(), is(23.5));
    }
}
