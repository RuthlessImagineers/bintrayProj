package utils;

import java.util.HashMap;
import java.util.List;

public class DeviceServerMapper {
    static HashMap<String, Integer> deviceToServerPortMapping = new HashMap<>();

    public static HashMap<String, Integer> getDeviceToPortMapping() {

        List<String> attachedDevices = new ADBUtil().getUdidOfAttachedDevices();
        HashMap<String, Integer> deviceToServerPortMapping = new HashMap<>();
        int startingPort = 4723;

        for (String attachedDevice : attachedDevices) {
            deviceToServerPortMapping.put(attachedDevice, startingPort);
            startingPort = startingPort + 20;
        }
        return deviceToServerPortMapping;
    }
}
