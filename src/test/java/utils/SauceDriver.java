package utils;

import org.openqa.selenium.remote.DesiredCapabilities;

public class SauceDriver {

    DesiredCapabilities desiredCapabilities;
    static String url;

    public SauceDriver() {
        desiredCapabilities = new SauceCapabilitiesBuilder().build();
        setUrl();
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    public static String getUrl() {
        return url;
    }

    public void setUrl() {
        url = String.format("http://%s:%s@ondemand.saucelabs.com:80/wd/hub",Properties.sauce_username,Properties.sauce_accesskey);
    }

}
