package de.slackspace.smartnightstand.web.dto;

public class LightResponse {

    private boolean isActive;

    public LightResponse(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
