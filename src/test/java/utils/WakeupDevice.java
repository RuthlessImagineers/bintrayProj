package utils;

import steps.StartingSteps;

/**
 * Created by krishnanand on 12/08/16.
 */
public class WakeupDevice implements Runnable {

    private DevicePower devicePower;
    private StartingSteps startingSteps;
    private boolean scenarioCompleted;

    public WakeupDevice(DevicePower devicePower, StartingSteps startingSteps) {
        this.devicePower = devicePower;
        this.startingSteps = startingSteps;
    }

    @Override
    public void run() {
        try {
            while (!scenarioCompleted) {
                Thread.sleep(500);
                new ADBUtil().wakeUpDevice();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
