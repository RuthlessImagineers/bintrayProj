package utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class AppiumServer {

    public void start(Properties properties) throws IOException, InterruptedException {
        String port = properties.getProperty("appiumPort");
        String udid = properties.getProperty("udid");

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/Applications/Appium.app/Contents/Resources/node/bin/node"))
                .withAppiumJS(new File("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(Integer.parseInt(port))
                .withArgument(GeneralServerFlag.ROBOT_ADDRESS, udid)
                .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, ((port) + 2))
                .withLogFile(new File("target/device.log")));

        System.out.println("Starting an appium instance");
        service.start();
    }
}
