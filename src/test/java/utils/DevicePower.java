package utils;


import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by krishnanand on 12/08/16.
 */
public class DevicePower {

    private int screenOffTimeout;
    private int screenDimDuration;
    private boolean powerState;



    public int getScreenOffTimeout() {
        return screenOffTimeout;
    }

    public void setScreenOffTimeout(String screenOffTimeoutLine) {
        this.screenOffTimeout = getDuration(screenOffTimeoutLine);
    }

    public int getScreenDimDuration() {
        return screenDimDuration;
    }

    public void setScreenDimDuration(String screenDimDurationLine) {
        this.screenDimDuration = getDuration(screenDimDurationLine);
    }

    public boolean isPowerOn() {
        return powerState;
    }

    public void setPowerState(String powerStateLine) {
        this.powerState = getPowerState(powerStateLine);
    }

    private int getDuration(String line) {
        int duration = 0;
        String[] splitter = line.split(":");
        String timeStr = splitter[1].trim();
        String actualTime = timeStr.split(" ")[0].trim();
        duration = NumberUtils.toInt(actualTime);
        return duration;
    }

    private boolean getPowerState(String line) {
        boolean powerState = false;
        String[] splitter = line.split("=");
        String state = splitter[1].trim();
        powerState = Boolean.getBoolean(state);
        return powerState;
    }
}
