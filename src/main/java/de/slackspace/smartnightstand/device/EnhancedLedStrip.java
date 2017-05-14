package de.slackspace.smartnightstand.device;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.smartnightstand.device.api.LedStrip;

public class EnhancedLedStrip implements LedStrip {

    private Log logger = LogFactory.getLog(getClass());

    private List<Led> currentLedConfig = new ArrayList<>();
    private int size = 150;
    private BrickletLEDStrip ledStrip;
	
	public EnhancedLedStrip(BrickletLEDStrip ledStrip) {
        this.ledStrip = ledStrip;
	} 
	
	@Override
    public void turnOff() {
		// turn off all leds
	    setAllLeds
		((short)0, (short)0, (short)0);
	}
	@Override
    public void setAllLeds(String colorHexTriplet) {
		Color color = Color.decode(colorHexTriplet);
		short red = (short) color.getRed();
		short green = (short) color.getGreen();
		short blue = (short) color.getBlue();
			
		setAllLeds(red, green, blue);
	}
	@Override
    public void setAllLeds(short red, short green, short blue) {
		try {
			short[] r = new short[16];
	        short[] g = new short[16];
	        short[] b = new short[16];
	        
	        for (int i = 0; i < 16; i++) {
	        	r[i] = red;
	        	g[i] = green;	
	        	b[i] = blue;	
			}
	        
	        // set all leds
	        for (int i = 0; i < size; i += 16) {
	        	ledStrip.setRGBValues(i, (short)16, b, r, g);	
			}
		} catch (TimeoutException | NotConnectedException e) {
		    logger.error("Could not set leds. Error was: ", e);
		}
	}
	
	@Override
    public void setSingleLed(int index, String colorHexTriplet) {
		Color color = Color.decode(colorHexTriplet);
		short red = (short) color.getRed();
		short green = (short) color.getGreen();
		short blue = (short) color.getBlue();
			
		setSingleLed(index, red, green, blue);
	}
	
	@Override
    public void setSingleLed(int index, short red, short green, short blue) {
		try {
			// calculate offset from beginning
			int offset = index / 16;
			
			// calculate inner offset in array
			int pixelIdx = index % 16;
			
			short[] r = new short[16];
	        short[] g = new short[16];
	        short[] b = new short[16];
	        
	        r[pixelIdx] = red;
        	g[pixelIdx] = green;	
        	b[pixelIdx] = blue;
	        
        	ledStrip.setRGBValues(offset * 16, (short)16, b, r, g);
		} catch (TimeoutException | NotConnectedException e) {
		    logger.error("Could not set single led. Error was: ", e);
		}
	}
	
	@Override
    public void setLeds(List<Led> leds) {
		try {
		    currentLedConfig = leds;
			HashMap<Integer, LedGroup> ledGroups = new HashMap<>();
	        
			for (Led led : leds) {
				int offset = led.getIndex() / 16;
				int pixelIdx = led.getIndex() % 16;
				
				LedGroup ledGroup = ledGroups.get(offset);
				if(ledGroup == null) {
					ledGroup = new LedGroup();
					ledGroups.put(offset, ledGroup);
				}
				
				ledGroup.getRed()[pixelIdx] = led.getRed();
				ledGroup.getGreen()[pixelIdx] = led.getGreen();	
				ledGroup.getBlue()[pixelIdx] = led.getBlue();
			}
			
			for (Integer groupOffset : ledGroups.keySet()) {
				LedGroup ledGroup = ledGroups.get(groupOffset);
				
				ledStrip.setRGBValues(groupOffset * 16, (short)16, ledGroup.getBlue(), ledGroup.getRed(), ledGroup.getGreen());
			}
		} catch (TimeoutException | NotConnectedException e) {
		    logger.error("Could not set leds. Error was: ", e);
		}
	}
	
	@Override
    public void setRangeLeds(int fromIndex, int size, String colorHexTriplet) {
		List<Led> leds = new ArrayList<>();
		leds = prepareRangeLeds(fromIndex, size, colorHexTriplet, leds);
		setLeds(leds);
	}
	
	@Override
    public void setRangeInverseLeds(int fromIndex, int size, String colorHexTriplet) {
		List<Led> leds = new ArrayList<>();
		leds = prepareRangeInverseLeds(fromIndex, size, colorHexTriplet, leds);
		setLeds(leds);
	}
	
	@Override
    public List<Led> prepareRangeLeds(int fromIndex, int size, String colorHexTriplet, List<Led> leds) {
		Color color = Color.decode(colorHexTriplet);
		return prepareRangeLeds(fromIndex, size, (short)color.getRed(), (short)color.getGreen(), (short)color.getBlue(), leds);
	}
	
	@Override
    public List<Led> prepareRangeLeds(int fromIndex, int size, short red, short green, short blue, List<Led> leds) {
		for (int i = fromIndex; i < fromIndex + size + 1; i++) {
			leds.add(new Led(i, red, green, blue));
		}
		
		return leds;
	}
	
	@Override
    public List<Led> prepareRangeInverseLeds(int fromIndex, int size, String colorHexTriplet, List<Led> leds) {
		for (int i = fromIndex; i > fromIndex - size; i--) {
			leds.add(new Led(i, colorHexTriplet));
		}
		
		return leds;
	}
	
	@Override
    public int getSize() {
		return size;
	}

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public List<Led> getCurrentLedConfig() {
        return currentLedConfig;
    }
}
