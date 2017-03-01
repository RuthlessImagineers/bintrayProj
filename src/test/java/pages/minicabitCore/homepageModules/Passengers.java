package pages.minicabitCore.homepageModules;

import entity.TripDetails;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import utils.DriverFactory;

import static utils.Constants.*;
import java.sql.Driver;

public class Passengers extends BasePage {

    @FindBy(id = "passengersCountTv")
    private WebElement passengerCount;

    @FindBy(id = "action_passengers_done")
    private WebElement selectPassengers;

    @FindBy(id = "seekBar")
    private WebElement seekBar;

    private AppiumDriver driver;

    private static Logger passengersLogger;

    public Passengers() throws Exception {
        driver = DriverFactory.getDriver();
        refreshElements(this);
        passengersLogger = LoggerFactory.getLogger(this.getClass());
    }

    public Passengers chooseNoOfPassengers() {
        waitForElementToBeVisible(passengerCount);
        int passengers = pageStore.get(TripDetails.class).getPassengers();
        if(passengers!=1) {
            passengersLogger.info(String.format("Passengers ready for travel %s", passengers));
            choosePassengers(passengers);
            logger.info("Passengers selected -- "+passengerCount.getText());
        } else {
            logger.info("Ignoring the seeking of passengers as the requested passengers is already selected -- "+passengerCount.getText());
        }
        selectPassengers.click();
        return this;
    }

    /**
     * You can choose number of passengers to travel.
     * <logic>
     *     This method uses the TouchAction class to perform the seekbar drag.
     *     xStartingpoint - Denotes the first point of seekbar
     *     yStartingpoint - Denotes the height of the seekbar
     *     xEndingpoint - Denotes the maximum width of the seekbar
     *     yEndingpoint - Denotes the maximum height of the seekbar
     *     xSinglePassengerLength - Denotes the minimum length to drag for a single passenger
     *     xLastPointToGoTo - Denotes the last point for attaining passengers to select
     * </logic>
     * @param passengers
     */
    private void choosePassengers(int passengers) {
        int xStartingpoint = seekBar.getLocation().getX();
        int yStartingpoint = seekBar.getLocation().getY();
        int xEndingpoint = seekBar.getSize().getWidth();
        int yEndingpoint = seekBar.getSize().getHeight();
        int xSinglePassengerLength = xEndingpoint/MAXIMUM_PASSENGERS;
        int xLastPointToGoTo = xSinglePassengerLength*(passengers);
        TouchAction seekBarAction = new TouchAction(driver);
        seekBarAction.longPress(xStartingpoint,yStartingpoint)
                .moveTo(xLastPointToGoTo,yEndingpoint).release().perform();

    }
}
