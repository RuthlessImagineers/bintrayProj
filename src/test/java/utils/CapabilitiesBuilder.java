package utils;

import enums.Drivers;
import exceptions.MissingPropertyException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CapabilitiesBuilder {

    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private static String UDID;
    private static String port;
    private PropertiesReader properties;

    public CapabilitiesBuilder(String property) {
        UDID = setUDID(property);
        port = setPort(property);
        buildCapabilities();
    }

    private void buildCapabilities() {
        properties = new PropertiesReader();
        File app = getAppFile(properties.getAppDir(), properties.getAppName());
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("platformName", properties.getPlatformName());
        capabilities.setCapability("deviceName", properties.getDeviceName());
        capabilities.setCapability("launchTimeout", Integer.parseInt(properties.getLaunchTimeout()));
        capabilities.setCapability("newCommandTimeout", 60);
        capabilities.setCapability("platformVersion", properties.getPlatformVersion());
        capabilities.setCapability("udid", UDID);
        capabilities.setCapability("noSign", true);
        if (DeviceChecker.isAndroid()) {
            capabilities.setCapability("appActivity", properties.getAppActivity());
            capabilities.setCapability("appPackage", properties.getAppPackage());
        }
        else {
            capabilities.setCapability("bundleId", properties.getAppPackage());
        }
        capabilities.setCapability("stopAppOnReset", false);
        capabilities.setCapability("autoAcceptAlerts", true);
    }

    public static String getPort() {
        return port;
    }

    private String setPort(String property) {
        String deviceToPortMap = property;
        return deviceToPortMap.split("=")[1];
    }

    public static String getUDID() {
        return UDID;
    }

    private String setUDID(String property) {
        String deviceToPortMap = property;
        return deviceToPortMap.split("=")[0];
    }

    public DesiredCapabilities build() {
        return capabilities;
    }

    public DesiredCapabilities getCapabilities(Properties properties) throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        File app = getAppFile(properties.getProperty("appDir"), properties.getProperty("appName"));
        capabilities.setCapability("app", app.getAbsolutePath());

        capabilities.setCapability("platformName", properties.getProperty("platformName"));
        capabilities.setCapability("deviceName", properties.getProperty("deviceName"));
        capabilities.setCapability("launchTimeout", properties.getProperty("launchTimeout"));
        capabilities.setCapability("newCommandTimeout", 60);
        capabilities.setCapability("platformVersion", properties.getProperty("platformVersion"));
        capabilities.setCapability("udid", UDID);
        capabilities.setCapability("noSign", true);
        if (DeviceChecker.isAndroid()) {
            capabilities.setCapability("appActivity", properties.getProperty("appActivity"));
            capabilities.setCapability("appPackage", properties.getProperty("appPackage"));
        }
        else {
            capabilities.setCapability("bundleId", properties.getProperty("appPackage"));
        }
        capabilities.setCapability("stopAppOnReset", false);
        capabilities.setCapability("autoAcceptAlerts", true);
        return capabilities;
    }


    private static File getAppFile(String appLocation, String appName) {
        File appDir = new File(appLocation);
        return new File(appDir, appName);
    }
}
