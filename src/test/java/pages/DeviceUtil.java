package pages;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static utils.DriverFactory.getDriver;

public class DeviceUtil extends BasePage {

    AppiumDriver driver;

    public DeviceUtil() throws Exception {
        this.driver = getDriver();
    }

    public String takeAScreenShotOfApp(String methodName) throws IOException {
        org.openqa.selenium.WebDriver augmentedDriver = new Augmenter().augment(driver);

        String screenShotFolder = "toast_img";
        createFolder(screenShotFolder);
        File screenShotSourceFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        String fileName = String.format("%s", methodName);
        File screenShotTargetFile = getTargetFile(screenShotFolder, fileName, "png");
        FileUtils.copyFile(screenShotSourceFile, screenShotTargetFile);
        return fileName;
    }

    private void createFolder(String folderName) throws IOException {
        if (!(new File(folderName).exists())) new File(folderName).mkdir();
    }

    private File getTargetFile(String folderName, String fileName, String ext) throws IOException {
        String rootPath = new File("./build").getCanonicalPath();
        String fullPath = String.format("%s//%s//%s.%s", rootPath, folderName, fileName, ext);
        return new File(fullPath);
    }

    public void toggleNetworkOnAndroid(String ON_OFF) {
        boolean network_toggle = false;
        if (ON_OFF.equalsIgnoreCase("on"))
            network_toggle = true;
        else if (ON_OFF.equalsIgnoreCase("off"))
            network_toggle = false;

        boolean wifiEnabled = ((AndroidDriver) driver).getNetworkConnection().wifiEnabled();
        boolean dataEnabled = ((AndroidDriver) driver).getNetworkConnection().dataEnabled();
        if (wifiEnabled == network_toggle && dataEnabled == network_toggle)
            return;

        NetworkConnectionSetting networkConnection = new NetworkConnectionSetting(false, true, true);

        networkConnection.setData(network_toggle);
        networkConnection.setWifi(network_toggle);

        ((AndroidDriver) driver).setNetworkConnection(networkConnection);
    }

    public void naivgateBackOnAndroid() {
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
    }

    public void toggleLocation(String on_off) throws IOException {
        String enablelocation = "adb shell settings put secure location_providers_allowed gps ";
        String disablelocation = "adb shell settings put secure location_providers_allowed ' ' ";

        Runtime runtime = Runtime.getRuntime();
        if (on_off.equalsIgnoreCase("on"))
            runtime.exec(enablelocation);
        else if (on_off.equalsIgnoreCase("off"))
            runtime.exec(disablelocation);
    }

    public void setFakeGPSLocation() throws Exception {
        String notification = new NotificationPage().getNotification("Fake GPS");
        if (notification != null) {
            System.out.println("GPS Location is already set");
        } else {
            System.out.println("Activating Fake GPS");
            setLocation("Central Jakarta");
        }
    }

    public void setLocation(String location) {
        ((AndroidDriver) driver).startActivity("com.lexa.fakegps", "com.lexa.fakegps.Main");

        By searchLocation = By.xpath("//*[@content-desc='Search']");
        By searchLocationPopupTextBox = By.xpath("//android.widget.EditText[@text='Type an address or zip']");
        By okayButton = By.xpath("//android.widget.Button[@text='OK']");
        By setLocationButton = By.xpath("//android.widget.Button[@text='Set location']");

        waitForElementToBeClickable(searchLocation);
        driver.findElement(searchLocation).click();

        waitForElementToBeClickable(searchLocationPopupTextBox);
        driver.findElement(searchLocationPopupTextBox).sendKeys(location);
        driver.findElement(okayButton).click();

        waitForElementToBeClickable(setLocationButton);
        driver.findElement(setLocationButton).click();
    }

}
