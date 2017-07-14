package de.slackspace.smartnightstand.behavior;

import org.junit.Test;

import de.slackspace.smartnightstand.device.LedStripProvider;
import de.slackspace.smartnightstand.device.api.LedStrip;
import de.slackspace.smartnightstand.device.config.TinkerforgeProperties;

public class KnightRiderModeTest {

    @Test
    public void testKnightRiderMode() {
        TinkerforgeProperties properties = new TinkerforgeProperties();
        properties.setHost("localhost");
        properties.setLedStripUid("7xwQ9g");
        properties.setUseMocks(false);
        LedStripProvider provider = new LedStripProvider(properties);

        LedStrip ledStrip = provider.createLedStrip();

        KnightRiderMode mode = new KnightRiderMode(ledStrip, 50, 120, 0, 42, 20);

        ledStrip.addFrameRenderedListener(mode);
        mode.frameRendered(0);

        while (true) {

        }
    }
}
