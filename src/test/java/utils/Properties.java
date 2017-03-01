package utils;

public class Properties {

    private static final PropertiesReader propertiesReader = new PropertiesReader();

    public static final String appDir = propertiesReader.getAppDir();
    public static final String appName = propertiesReader.getAppName();

    public static final String platformName = propertiesReader.getPlatformName();
    public static final String platformVersion = propertiesReader.getPlatformVersion();
    public static final String deviceName = propertiesReader.getDeviceName();
    public static final String bundleId = propertiesReader.getBundleId();
    public static final String appPackage = propertiesReader.getAppPackage();
    public static final String udid = propertiesReader.getUdid();
    public static final String launchTimeout = propertiesReader.getLaunchTimeout();
    public static final String url = propertiesReader.getUrl();
    public static final String appActivity = propertiesReader.getAppActivity();
    public static final String model = propertiesReader.getModel();
    public static final String appiumPort = propertiesReader.getAppiumPort();


    //Sauce properties starts here
    public static final String sauce_username = propertiesReader.getSauceUsername();
    public static final String sauce_accesskey = propertiesReader.getSauceAccessKey();
    public static final String sauce_appiumVersion = propertiesReader.getSauceAppiumVersion();
    public static final String sauce_deviceName = propertiesReader.getSauceDeviceName();
    public static final String sauce_deviceType = propertiesReader.getSauceDeviceType();
    public static final String sauce_deviceOrientation = propertiesReader.getSauceDeviceOrientation();
    public static final String sauce_browserName = propertiesReader.getSauceBrowserName();
    public static final String sauce_platformVersion = propertiesReader.getSaucePlatformVersion();
    public static final String sauce_platfromName = propertiesReader.getSaucePlatformName();
    public static final String sauce_app = "sauce-storage:"+propertiesReader.getSauceApp();
}
