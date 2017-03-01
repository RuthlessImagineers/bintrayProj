package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static utils.DriverFactory.getDriver;

/**
 * Created by nishant on 27/06/16.
 */
public class NotificationPage extends BasePage {

    AppiumDriver driver;

    @FindBy(id = "com.android.systemui:id/notification_panel")
    private List<WebElement> notificationPanel;

    @FindBy(id = "com.android.systemui:id/clear_all_button")
    private List<WebElement> clearAllBtn;

    @FindBy(id = "com.android.systemui:id/latestItems")
    private List<WebElement> latestItemsContainer;

    @FindBy(id = "android:id/status_bar_latest_event_content")
    private List<WebElement> latestItemsContent;

    @FindBy(id = "android:id/title")
    private List<WebElement> itemTitle;

    @FindBys({
            @FindBy(id = "android:id/big_text"),
            @FindBy(id = "android:id/text")
    })
    private List<WebElement> itemText;

    String itemText_Phone_Locator_Text = "android:id/text";
    String itemTitle_Locator_Text = "android:id/title";
    String itemText_Tablet_Locator_Text = "android:id/big_text";

    public NotificationPage() throws Exception {
        this.driver = getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getItemTitle(int num) {
        return latestItemsContent.get(num).findElement(By.id(itemTitle_Locator_Text)).getText();
    }

    public int getLastItemsContentSize() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("android:id/status_bar_latest_event_content")));
            return latestItemsContent.size();
        } catch (Exception e) {
        }
        if (driver.findElements(By.xpath("//*[@text='Silent']")).size() != 0) {
            swipeFromTo(driver.findElement(By.xpath("//*[@text='Silent']")), driver.findElement(By.xpath("//*[@text='Lock']")));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("android:id/status_bar_latest_event_content")));
            return latestItemsContent.size();
        }
        return 0;
    }

    public String getItemText(int num) throws InterruptedException {
        if (latestItemsContent.get(num).findElements(MobileBy.id(itemText_Tablet_Locator_Text)).isEmpty()) {
            return latestItemsContent.get(num).findElement(MobileBy.id(itemText_Phone_Locator_Text)).getText();
        } else {
            return latestItemsContent.get(num).findElement(MobileBy.id(itemText_Tablet_Locator_Text)).getText();
        }
    }

    public String getNotification(String notificationTitle) throws InterruptedException {
        ((AndroidDriver) driver).openNotifications();
        int itemsListSize = getLastItemsContentSize();
        for (int i = 0; i < itemsListSize; i++) {
            if (getItemTitle(i).equals(notificationTitle)) {
                String itemText = getItemText(i);
                ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
                return itemText;
            }
        }
        ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.BACK);
        return null;
    }

    public void checkForGoJekNotification(String expectedNotification) throws Exception {
        String[] parts = expectedNotification.split(Pattern.quote("..."));
        String actualNotification = getNotification("GO-JEK Staging");
        if (actualNotification != null) {
            for (int i = 0; i < parts.length; i++)
                Assert.assertTrue("Actual : " + actualNotification
                        + " \n Expected : " + expectedNotification, actualNotification.contains(parts[i]));
        } else
            Assert.fail("Notification not getting triggered");
    }
}