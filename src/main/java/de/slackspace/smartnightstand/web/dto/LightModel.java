package de.slackspace.smartnightstand.web.dto;

public class LightModel {

    private boolean isActive;
    
    public LightModel() {
		// used for DI
	}

    public LightModel(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
