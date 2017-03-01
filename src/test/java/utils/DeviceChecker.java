package utils;

import org.openqa.selenium.Platform;

/**
 * Created by nishant on 06/04/16.
 */
public class DeviceChecker {

    public static boolean isAndroid() {
        return Properties.platformName.equalsIgnoreCase(String.valueOf(Platform.ANDROID));
    }
}
