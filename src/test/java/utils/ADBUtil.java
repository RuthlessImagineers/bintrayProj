package utils;

import com.google.common.annotations.Beta;
import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ADBUtil {

    public static final String DEVICE_ID = new PropertiesReader().getUdid();
    public static final String port = new PropertiesReader().getPort();
    private static final String SCREEN_OFF_TIMEOUT  = "Screen off timeout:";
    private static final String SCREEN_DIM_DURATION = "Screen dim duration:";
    private static final String POWER_STATE = "Display Power:";

    public StringBuilder readAdbLog(String cmd) {
        String command = cmd;
        String line;
        StringBuilder log = new StringBuilder();
        Process process;
        Runtime rt = Runtime.getRuntime();
        try {
            process = rt.exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));


            while ((line = stdInput.readLine()) != null) {
                log.append(line);
                log.append(System.getProperty("line.separator"));
            }
            while ((line = stdError.readLine()) != null) {
                log.append(line);
                log.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }

    public List<String> getUdidOfAttachedDevices() {
        List<String> devices = new ArrayList<>();
        Scanner scan = new Scanner(String.valueOf(readAdbLog("adb devices")));
        while (scan.hasNextLine()) {
            String oneLine = scan.nextLine();
            if (oneLine.endsWith("device")) {
                devices.add(oneLine.replace("device", "").trim());
            }
        }
        return devices;
    }


    public String getBookingId() {
        Scanner scan = new Scanner(String.valueOf(readAdbLog(String.format("adb -s %s logcat -d", DEVICE_ID))));
        while (scan.hasNextLine()) {
            String oneLine = scan.nextLine().replaceAll(" ", "");
            if (oneLine.contains("\"orderNo\":")) {
                String[] test = oneLine.split("\"id\":");
                String afterBookingId = test[test.length - 1];
                String[] split = afterBookingId.split(",");
                return (split[0]);
            }
        }
        return "Couldn't find booking id";
    }

    public String getBookingIdGoKilat() {
        Scanner scan = new Scanner(String.valueOf(readAdbLog(String.format("adb -s %s logcat -d", DEVICE_ID))));
        while (scan.hasNextLine()) {
            String oneLine = scan.nextLine();
            if (oneLine.contains("v1/consumer/bookings/GK-")) {
                String[] split = oneLine.split("GK-");
                String bookingId = split[1].split(" ")[0];
                return (bookingId);
            }

        }
        return "Couldn't find booking id";
    }


    public void clearAdbLog() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec(String.format("adb -s %s logcat -c", DEVICE_ID));
    }

    public String getCustomerId() {
        Scanner scan = new Scanner(String.valueOf(readAdbLog(String.format("adb -s %s logcat -d", DEVICE_ID))));
        while (scan.hasNextLine()) {
            String oneLine = scan.nextLine();
            if (oneLine.contains("customerId=")) {
                String[] test = oneLine.split("customerId=");
                String afterCustomerId = test[test.length - 1];
                String[] split = afterCustomerId.split(",");
                return (split[0]);
            }
        }
        return "Couldn't find customer id";
    }

    public void dumpADBLog(Scenario scenario) throws IOException {
        String dumpADBLog = readAdbLog(String.format("adb -s %s logcat -d", DEVICE_ID)).toString();
        String logsFolderName = "adblogs";
        String fileName = scenario.getId();
        createFolder("build", logsFolderName);
        File targetFile = getTargetFile(logsFolderName, fileName, "log");
        FileUtils.writeStringToFile(targetFile, dumpADBLog);
    }

    private void createFolder(String parentFolder, String folderName) {
        if (!(new File(parentFolder, folderName).exists())) new File(parentFolder, folderName).mkdir();
    }

    private File getTargetFile(String folderName, String fileName, String ext) throws IOException {
        String rootPath = new File("./build").getCanonicalPath();
        String fullPath = String.format("%s//%s//%s.%s", rootPath, folderName, fileName, ext);
        return new File(fullPath);
    }

    public void resetDevicePort() {
        // Work in progress
        // Get the list of process
        // choose the process based on port number
        // kill the process
    }

    public void captureVideo(String fileName) throws IOException {
        //adb shell screenrecord /sdcard/INSERT-FILE-NAME.mp4
        Runtime rt = Runtime.getRuntime();
        rt.exec(String.format("adb -d shell screenrecord /sdcard/%s.mp4", fileName));
    }

    public void captureVideo(String fileName, String deviceID) throws IOException {
        //adb shell screenrecord /sdcard/INSERT-FILE-NAME.mp4
        Runtime rt = Runtime.getRuntime();
        rt.exec(String.format("adb -d shell %s screenrecord /sdcard/%s.mp4", deviceID,fileName));
    }

    public void importRecordedVideo(String fileName) throws Exception {
//        adb pull /sdcard/RECORDED-FILE-NAME.mp4 C:/example file name.mp4
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec(String.format("adb -d pull /sdcard/%s.mp4 ./build", fileName));
        while (process.isAlive()) {
            Thread.sleep(100);
        }
    }

    public void importRecordedVideo(String fileName, String deviceID) throws Exception {
//        adb pull /sdcard/RECORDED-FILE-NAME.mp4 C:/example file name.mp4
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec(String.format("adb -d %s pull /sdcard/%s.mp4 ./build",deviceID, fileName));
        while (process.isAlive()) {
            Thread.sleep(100);
        }
    }


    public void deleteCapturedVideoOnDevice(String fileName) throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec(String.format("adb -d shell rm -f /sdcard/%s.mp4",fileName));
    }

    public void deleteCapturedVideoOnDevice(String fileName, String deviceID) throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec(String.format("adb -d %s shell rm -f /sdcard/%s.mp4",deviceID,fileName));
    }

    public void killScreenrecordProcess() throws IOException {
        String command = "adb -d shell ps |grep screenrecord";
        String log = readAdbLog(command).toString();
        if(log.length()>0) {
            String pid = getPID(log.trim());
            String killScreenrecordProcess = String.format("adb -d shell kill -2 %s", pid);
            Runtime rt = Runtime.getRuntime();
            rt.exec(killScreenrecordProcess);
        }
    }

    public void killScreenrecordProcess(String deviceID) throws IOException {
        String command = String.format("adb -d %s shell ps |grep screenrecord",deviceID);
        String log = readAdbLog(command).toString();
        if(log.length()>0) {
            String pid = getPID(log.trim());
            String killScreenrecordProcess = String.format("adb -d %s shell kill -2 %s",deviceID, pid);
            Runtime rt = Runtime.getRuntime();
            rt.exec(killScreenrecordProcess);
        }
    }

    /**
     * For now requires exact process in below format
     * USER      PID   PPID  VSIZE  RSS   WCHAN            PC  NAME
     * @param processLog
     */
    private String getPID(String processLog) {
        String[] processDetails = getStringWithAPipe(processLog).split("\\|");
        String pid = processDetails[1];
        return pid;
    }

    /**
     * Retrives desired processname from process log
     * @param processName
     * @param processLog
     */
    @Beta
    private void getPID(String processName, String processLog) {

    }

    private String getStringWithAPipe(String strToMatch) {
        String[] stringWithSpace = strToMatch.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for(String str : stringWithSpace) {
            if(!str.equals("")) {
                int nbspCode = str.toCharArray()[0];
                if (nbspCode != 160)
                    stringBuilder.append(str + "|");
            }
        }
        return stringBuilder.toString();
    }

    public DevicePower getDevicePowerDetails() {
        DevicePower devicePower = new DevicePower();
        String line = null;
        String command = "adb -e shell dumpsys power";
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(command);
            BufferedReader stdReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line=stdReader.readLine())!=null) {
                if(line.contains(SCREEN_OFF_TIMEOUT)) {
                    devicePower.setScreenOffTimeout(line);
                } else if(line.contains(SCREEN_DIM_DURATION)) {
                    devicePower.setScreenDimDuration(line);
                } else if(line.contains(POWER_STATE)) {
                    devicePower.setPowerState(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devicePower;
    }

    public void wakeUpDevice() throws IOException {
        String command = "adb -e shell input keyevent KEYCODE_WAKEUP";
        Runtime rt = Runtime.getRuntime();
        rt.exec(command);
    }


    public List<String> getAttachedDevices() {
        List<String> devicesList = new ArrayList<>();
        String command = "adb devices";
        StringBuilder builder = readAdbLog(command);
        String[] adbDevices = builder.toString().split(System.getProperty("line.separator"));
        int port = 4723;
        for(String adbDevice : adbDevices) {
            if(!adbDevice.contains("List of devices attached")) {
                if(adbDevice.contains("device")) {
                    devicesList.add(String.format(adbDevice.split("\t")[0] + "=%s", port));
                    port = port + 20;
                }
            }
        }
        return devicesList;
    }

}
