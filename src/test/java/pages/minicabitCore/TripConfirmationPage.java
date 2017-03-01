package pages.minicabitCore;

import entity.DatePicker;
import entity.TripDetails;
import entity.TripSummary;
import enums.TripType;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import utils.locatorUtils.Bys;


import static utils.Constants.*;
import static org.junit.Assert.assertEquals;

public class TripConfirmationPage extends BasePage {

    private TripDetails tripDetails;
    private AppiumDriver driver;

    @FindBy(id = "totalPriceTv")
    private WebElement totalPrice;

    @FindBy(id = "fromInfoTv")
    private WebElement pickup;

    @FindBy(id = "toInfoTv")
    private WebElement dropOff;

    @FindBy(id = "pickUpTimeTv")
    private WebElement pickupTime;

    @FindBy(id = "passengersCountTv")
    private WebElement passengerCount;

    @FindBy(id = "luggageCountTv")
    private WebElement luggageCount;

    @FindBy(id = "trip_summary_continue_btn")
    private WebElement continue_btn;

    @FindBy(id = "pickUpDateTv")
    private WebElement pickupDate;

    @FindBy(id = "trip_confirmation_booking_ref")
    private WebElement tripBookingConfirmationRef;

    @FindBy(id = "viaInfoTv")
    private WebElement viaInfo;

    @FindBy(id = "action_trip_confirmation_done")
    private WebElement tripConfirmationDone;

    @FindBy(id = "providerNameTv")
    private WebElement providerName;


    private TripSummary tripSummary = pageStore.get(TripSummary.class);
    public TripConfirmationPage() throws Exception {
        this.tripDetails = pageStore.get(TripDetails.class);
        refreshElements(this);
        captureSummary(tripDetails.getTripType());
    }


    private TripConfirmationPage captureSummary(TripType tripType) {
        switch (tripType) {
            case SINGLEJOURNEY:
                captureSingleJourneySummary();
                verifySingleJourneyTripDetails();
                break;
            case JOURNEYWITHVIA:
                captureJourneyWithViaSummary();
                break;
            case RETURNJOURNEY:
                captureReturnJourneySummary();
                break;
        }
        return this;
    }

    private TripConfirmationPage captureSingleJourneySummary() {
            captureBaseSummary();
        return this;
    }

    private TripConfirmationPage captureReturnJourneySummary() {
            captureBaseSummary();
            setOnBoundDetails();
            setReturnDetails();
        return this;
    }

    private TripConfirmationPage captureJourneyWithViaSummary() {
            captureBaseSummary();
            setViaCount();

        return this;
    }

    private TripConfirmationPage captureBaseSummary() {
        waitForPresenceOfElement(Bys.BOOKING_REF_TEXT);
        tripSummary.setTripTotal(totalPrice.getText());
        tripSummary.setFrom(pickup.getText());
        tripSummary.setTo(dropOff.getText());
        tripSummary.setPickupTime(pickupTime.getText());
        tripSummary.setPickupDate(pickupDate.getText());
        tripSummary.setTotalLuggageCount(luggageCount.getText());
        tripSummary.setTotalNoOfPassengers(passengerCount.getText());
        tripSummary.setCabProviderName(providerName.getText());
        return this;
    }

    public TripConfirmationPage verifyTripDetails() {
        switch (tripDetails.getTripType()) {
            case SINGLEJOURNEY:
                verifySingleJourneyTripDetails();
                break;
            case JOURNEYWITHVIA:
                verifyJourneyWithViaTripDetails();
                break;
            case RETURNJOURNEY:
                verifyReturnJourneyTripDetails();
                break;
        }
        return this;
    }



    private TripConfirmationPage verifySingleJourneyTripDetails() {
        logger.info("Verifying Single journey trip details");
        verifyBasicTripDetails();
        return this;
    }

    private TripConfirmationPage verifyReturnJourneyTripDetails() {
        logger.info("Verifying return journey trip details");
        verifyBasicTripDetails();
        return this;
    }

    private TripConfirmationPage verifyJourneyWithViaTripDetails() {
        logger.info("Verifying via trip trip details");
        verifyBasicTripDetails();
        verifyVias();
        return this;
    }


    private TripConfirmationPage verifyBasicTripDetails() {
        logger.info("Verifying trip details");
        verifyPrice();
        verifyPickupLocation();
        verifyDropoffLocation();
        verifyPickupDate();
        verifyPickupTime();
        verifyLuggage();
        verifyPassengers();
        verifyBookingReference();
        return this;
    }

    public TripConfirmationPage verifyPickupLocation() {
        logger.info("Verifying pickup location -- "+tripSummary.getFrom());
        boolean matched = false;
        if(tripSummary.getFrom().contains(tripDetails.getLocation().getPickup())) {
            matched = true;
        }
        assertEquals(String.format("User has selected pickup " +
                "location as %s but his pickup location is changed to %s.",
                tripDetails.getLocation().getPickup(), tripSummary.getFrom()),matched,true);
        return this;
    }

    public TripConfirmationPage verifyDropoffLocation() {
        logger.info("Verifying dropoff location -- "+tripSummary.getTo());
        boolean matched = false;
        if(tripSummary.getTo().contains(tripDetails.getLocation().getDropoff())) {
            matched = true;
        }
        assertEquals(String.format("User has selected dropoff " +
                "location as %s but his dropoff location is changed to %s.",
                tripDetails.getLocation().getDropoff(), tripSummary.getTo()),matched,true);
        return this;
    }

    public TripConfirmationPage verifyPickupDate() {
        String actualDate = null;
        logger.info(String.format("Verifying pickup date -- %s",tripSummary.getPickupDate()));
        try {
            actualDate = pageStore.get(DatePicker.class).getDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(String.format("Expected pickup date is %s but found %s ",
                tripDetails.getPickupTime(), tripSummary.getPickupDate()),
                actualDate,tripSummary.getPickupDate());
        return this;
    }

    public TripConfirmationPage verifyPickupTime() {
        logger.info("Verifying pickup time -- "+tripSummary.getPickupTime());
        assertEquals(String.format("Expected pickup time %s but found %s - Do not worry it could mostly " +
                "be a false alarm as the script would have failed to " +
                "tap on time picker on landing page. Check out video for confirmation.",
                tripDetails.getPickupTime(), tripSummary.getPickupTime()),
                tripSummary.getPickupTime().contains(tripDetails.getPickupTime()), true);
        return this;
    }

    public TripConfirmationPage verifyPassengers() {
        logger.info("Verifying passengers -- "+tripSummary.getTotalNoOfPassengers());
        assertEquals(String.format("Expected passengers %s but found %s",
                tripDetails.getPassengers(), tripSummary.getTotalNoOfPassengers()),
                String.valueOf(tripDetails.getPassengers()),tripSummary.getTotalNoOfPassengers());
        return this;
    }

    public TripConfirmationPage verifyLuggage() {
        if(tripDetails.isWithLuggage()) {
            logger.info("Verifying luggage count -- " + tripSummary.getTotalLuggageCount());
            assertEquals(String.format("Expected luggage count %s but found %s",
                    tripDetails.getLuggage(), tripSummary.getTotalLuggageCount()),
                    String.valueOf(tripDetails.getLuggage()), tripSummary.getTotalLuggageCount());
        }
        return this;
    }

    public TripConfirmationPage verifyVias() {
            logger.info("Verifying vias -- " + tripSummary.getViaCount());
            if(tripDetails.getVias().size()==1) {
                String viaName = tripDetails.getVias().get(0).toString();
                assertEquals(String.format("Expected via is %s while " +
                        "the displayed via is %s",viaName,tripSummary.getViaCount()),
                        tripSummary.getViaCount().contains(viaName),true);
            } else {
                String viaCount = String.valueOf(tripDetails.getVias().size());
                assertEquals(String.format("Expected via count is %s while " +
                                "the displayed via is %s",viaCount,tripSummary.getViaCount()),
                        viaCount,tripSummary.getViaCount());
            }
        return this;
    }

    private boolean setViaCount() {
        boolean viasExist = false;
        try {
            if (viaInfo != null) {
                tripSummary.setViaCount(viaInfo.getText());
            }
            viasExist = true;
        } catch (NoSuchElementException e) {

        }
        return viasExist;
    }

    private TripConfirmationPage setOnBoundDetails() {

        return this;
    }

    private TripConfirmationPage setReturnDetails() {

        return this;
    }

    public TripConfirmationPage verifyPrice() {
        logger.info("Verifying trip price -- "+tripDetails.getTotalPrice());
        String tripDetailsPrice = tripDetails.getTotalPrice();
        String tripSummaryPrice = tripSummary.getTripTotal();
        assertEquals(String.format("Expected trip price is %s " +
                "while the price charged is %s",tripDetailsPrice,tripSummaryPrice),tripSummaryPrice.contains(tripDetailsPrice),true);
        return this;
    }

    public TripConfirmationPage verifyBookingReference() {
        waitForElementToBeVisible(tripBookingConfirmationRef);
        String bookingRef = tripBookingConfirmationRef.getText();
        logger.info("Verifying booking reference number -- "+bookingRef);
        assertEquals(String.format("Expected booking reference pattern is %s or %s  but found %s",BOOKING_REF_REGEX, BOOKING_REF_REGEX1,bookingRef),
                bookingRef.matches(BOOKING_REF_REGEX) || bookingRef.matches(BOOKING_REF_REGEX1),true);
        return this;
    }

    public Homepage closeTripConfirmationPage() {
        waitForElementToBeClickable(tripConfirmationDone);
        tripConfirmationDone.click();
        return pageStore.get(Homepage.class);
    }
}
