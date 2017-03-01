package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class SauceCapabilitiesBuilder {

    DesiredCapabilities capabilities = new DesiredCapabilities();

    public SauceCapabilitiesBuilder() {
        buildSauceCapabilities();
    }

    private void buildSauceCapabilities() {
        capabilities.setCapability("appiumVersion", Properties.sauce_appiumVersion);
        capabilities.setCapability("deviceName", Properties.sauce_deviceName);
        capabilities.setCapability("deviceType", Properties.sauce_deviceType);
        capabilities.setCapability("deviceOrientation", Properties.sauce_deviceOrientation);
        capabilities.setCapability("browserName", Properties.sauce_browserName);
        capabilities.setCapability("platformVersion",Properties.sauce_platformVersion);
        capabilities.setCapability("platformName",Properties.sauce_platfromName);
        capabilities.setCapability("app",Properties.sauce_app);
    }

    public DesiredCapabilities build() {

        return capabilities;
    }

}
