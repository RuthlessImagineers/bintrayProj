package utils;


import enums.Drivers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.SystemClock;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;


public class DriverFactory {

    protected static AppiumDriver driver;


    public static AppiumDriver initializeAppiumSession() throws Exception {
        DesiredCapabilities capabilities = CapabilitiesFactory.getCapabilities();
        driver = setUpDevice(capabilities);
        return driver;
    }

    private static AppiumDriver setUpDevice(DesiredCapabilities capabilities) throws MalformedURLException {
        String url = getURL();
        if (DeviceChecker.isAndroid())
            return new AndroidDriver(new URL(url), capabilities);
        return new IOSDriver(new URL(url), capabilities);
    }

    private static String getURL() {
        String driver = System.getProperty("driver");
        String url = "";
        switch (Drivers.valueOf(driver.toUpperCase())) {
            case LOCAL:
                    url = LocalDriver.getUrl();
                break;
            case SAUCE:
                url = SauceDriver.getUrl();
                break;
        }
        return url;
    }


    /*
    * Use this method when you want to use the web locator strategy
    * This will allow us to use CSS Selector
    * Once done, please switch to the Native context
    * */
    public static void changeDriverContextToWeb(AppiumDriver driver) {
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.contains("WEBVIEW"))
                DriverFactory.driver.context(contextName);
        }
    }

    /*
    * Use this method when you want to use the Native locator strategy
    * This will allow us to use xPath and IOSFindBy
    * */
    public static void changeDriverContextToNative(AppiumDriver driver) {
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            if (contextName.contains("NATIVE"))
                DriverFactory.driver.context(contextName);
        }
    }

    public static AppiumDriver getDriver() throws Exception {
            return driver;
    }

    public void killAppiumSession() {
        try {
            driver.quit();
        } catch (SessionNotFoundException e) {
            new ADBUtil().resetDevicePort();
        }
    }

    public static void setDriver(AppiumDriver driver) {
        DriverFactory.driver = driver;
    }
}
