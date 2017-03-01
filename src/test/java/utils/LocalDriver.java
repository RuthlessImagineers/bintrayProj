package utils;

import exceptions.MissingPropertyException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

public class LocalDriver {

    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    private static String portMapping="";
    private static String url;
    public LocalDriver() {
        this(getDeviceToPortMapping());
        setUrl();
    }

    public LocalDriver(String portMapping) {
        LocalDriver.portMapping = portMapping;
        desiredCapabilities = new CapabilitiesBuilder(portMapping).build();
        setUrl();
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }


    private static String getDeviceToPortMapping() {
        List<String> devicesList = null;
        String device = System.getProperty("device");
        if (System.getProperty("device") != null) {
            return System.getProperty("device");
        } else {
            devicesList = new ADBUtil().getAttachedDevices();
            if(devicesList.size()==0)
                throw new RuntimeException("No device or emulators attached, check for devices");
        }
        return devicesList.get(0);
    }

    public static String getPort() {
        return getDeviceToPortMapping().split("=")[1];
    }

    public static String getDevice() {
        return getDeviceToPortMapping().split("=")[0];
    }

    public static String getUrl() {
        return url;
    }

    public void setUrl() {
        url =  String.format("http://127.0.0.1:%s/wd/hub", getPort());
    }

}
