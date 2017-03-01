package pages.minicabitCore.TripSummaryPageModules;

import entity.TripDetails;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.Popup;
import pages.minicabitCore.PaymentPageModules.PaymentPage;
import pages.minicabitCore.TripConfirmationPage;
import utils.locatorUtils.Bys;
import utils.DriverFactory;

import java.util.List;

import static utils.Constants.CommonData.FLIGHT_NUMBER;

public class TripSummaryPage extends BasePage{
    private TripDetails tripDetails;
    private AppiumDriver driver;


//    @FindBy(id = "select_payment_tv")
//    private WebElement selectPaymentMode;

    @FindBy(id = "trip_summary_change_payment_method_rl")
    private WebElement selectPaymentMode;

    @FindBy(id = "payment_flight_number_et")
    private WebElement flightNum;

    @FindBy(id = "trip_summary_continue_btn")
    private WebElement confirmBooking_Btn;

    public TripSummaryPage() throws Exception {
        this.tripDetails = pageStore.get(TripDetails.class);
        this.driver = DriverFactory.getDriver();
        refreshElements(this);
    }


    public PaymentPage navToPayments() throws Exception {
        waitForElementToBeVisible(flightNum);
        List<WebElement> paymentMethod = driver.findElements(By.id("trip_summary_change_payment_method_rl"));
        if(paymentMethod.size()>0) {
            selectPaymentMode.click();
        }
        //waitForElementToBeClickable(selectPaymentMode);
        pageStore.get(Popup.class).handleNoCashPopup(PaymentPage.class);
        logger.info("Navigating user to payments page");
        return new PaymentPage();
    }

    public TripSummaryPage enterFlightNumber() {
        enterFlightNumberAs(FLIGHT_NUMBER);
        return this;
    }

    public TripSummaryPage enterFlightNumberAs(String flightNumber) {
        waitForElementToBeClickable(flightNum);
        logger.info("Entering flight number as "+flightNumber);
        sendKeys(flightNum,flightNumber);
        return this;
    }

    public TripConfirmationPage confirmBooking() {
        logger.info("Creating a booking on user request");
        waitForElementToBeClickable(confirmBooking_Btn);
        confirmBooking_Btn.click();
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
        return pageStore.get(TripConfirmationPage.class);
    }
}
