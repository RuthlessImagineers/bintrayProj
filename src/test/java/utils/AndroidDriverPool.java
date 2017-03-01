package utils;

import io.appium.java_client.android.AndroidDriver;

public class AndroidDriverPool {

    private static AndroidDriver riderInstance;
    private static AndroidDriver ojekInstance;

    public AndroidDriver getRiderInstance() {
        return riderInstance;
    }

    public void setRiderInstance(AndroidDriver riderInstance) {
        this.riderInstance = riderInstance;
    }

    public AndroidDriver getOjekInstance() {
        return ojekInstance;
    }

    public void setOjekInstance(AndroidDriver ojekInstance) {
        this.ojekInstance = ojekInstance;
    }
}
