package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private Properties deviceProperty = new Properties();
    private Properties appProperty = new Properties();
    private Properties sauceProperty = new Properties();
    private String port;

    public PropertiesReader() {
        try {

            String devicePropertiesFilePath = "android.properties";
            String saucePropertiesFilePath = "Sauce.properties";
            String appPropertiesFilePath = getTargetTestApp() +
                    ".properties";

            InputStream deviceInputStream = this.getClass().getClassLoader().getResourceAsStream(devicePropertiesFilePath);
            InputStream appInputStream = this.getClass().getClassLoader().getResourceAsStream(appPropertiesFilePath);
            InputStream sauceInputStream = this.getClass().getClassLoader().getResourceAsStream(saucePropertiesFilePath);
            deviceProperty.load(deviceInputStream);
            appProperty.load(appInputStream);
            sauceProperty.load(sauceInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getTargetTestApp() {
        if (System.getProperty("app") == null)
            throw new RuntimeException("no app name found");
        return System.getProperty("app");
    }

    public String getAppDir() {
        return appProperty.getProperty("appDir");
    }

    public String getAppName() {
        return appProperty.getProperty("appName");
    }

    public String getAppPackage() {
        return appProperty.getProperty("appPackage");
    }

    public String getAppActivity() {
        return appProperty.getProperty("appActivity");
    }

    public String getBundleId() {
        return deviceProperty.getProperty("bundleId");
    }

    public String getPlatformName() {
        return deviceProperty.getProperty("platformName");
    }

    public String getPlatformVersion() {
        return deviceProperty.getProperty("platformVersion");
    }

    public String getUdid() {
        if (new DeviceChecker().isAndroid()) {
            return CapabilitiesBuilder.getUDID();
        }
        return deviceProperty.getProperty("udid");
    }

    public String getDeviceName() {
        return deviceProperty.getProperty("deviceName");
    }

    public String getLaunchTimeout() {
        return deviceProperty.getProperty("launchTimeout");
    }

    public String getUrl() {
        return deviceProperty.getProperty("url");
    }

    public String getAvdName() {
        return deviceProperty.getProperty("avd");
    }

    public String getModel() {
        return deviceProperty.getProperty("model");
    }

    public String getAppiumPort() {
        return deviceProperty.getProperty("appiumPort");
    }

    public String getPort() {
        return CapabilitiesBuilder.getPort();
    }

    public String getEmailForDevice(String device) {
        return appProperty.getProperty(device);
    }


    //Sauce properties

    public String getSauceUsername() { return sauceProperty.getProperty("sauce_username"); }

    public String getSauceAccessKey() { return sauceProperty.getProperty("sauce_access_key"); }

    public String getSauceAppiumVersion() { return sauceProperty.getProperty("appiumVersion"); }

    public String getSauceDeviceName() { return sauceProperty.getProperty("deviceName"); }

    public String getSauceDeviceType() { return sauceProperty.getProperty("deviceType"); }

    public String getSauceDeviceOrientation() { return sauceProperty.getProperty("deviceOrientation"); }

    public String getSauceBrowserName() { return sauceProperty.getProperty("browserName"); }

    public String getSaucePlatformVersion() { return sauceProperty.getProperty("platformVersion"); }

    public String getSaucePlatformName() { return sauceProperty.getProperty("platformName"); }

    public String getSauceApp() { return sauceProperty.getProperty("app"); }
}
