package pages.minicabitCore;

import builders.FromBuilder;
import builders.ToBuilder;
import builders.TripDetailsBuilder;
import entity.*;
import entity.journeyDetails.JourneyWithVia;
import entity.journeyDetails.ReturnJourney;
import enums.*;
import exceptions.LocationException;
import interfaces.Journey;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;
import pages.Popup;
import pages.minicabitCore.homepageModules.*;
import pages.minicabitCore.homepageModules.Location;
import utils.Constants;
import utils.locatorUtils.Bys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static utils.Constants.DataFileNames.JOURNEY_DETALS;
import static utils.Constants.DataFileStrings;
import static utils.Constants.FolderLocations.TESTDATA;
import static utils.Constants.PlaceHolders.LOCATION_PLACEHOLDER;
import static utils.DriverFactory.getDriver;

public class Homepage extends BasePage {

    protected AppiumDriver driver;
    private TripDetails tripDetails;
    private String luggageDetails="";
    protected static Logger homepageLogger;

    @FindBy(id = "home")
    private WebElement homeBtn;

    @FindBy(id = "fromAddress")
    private WebElement fromAddress;

    @FindBy(id = "toAddress")
    private WebElement toAddress;

    @FindBy(id = "dayOrNightTextView")
    private WebElement home;

    @FindBy(id = "pick_up_time_button")
    private WebElement dateSelector;

    @FindBy(id = "main_screen_pick_return_btn")
    private WebElement returnDateSelector;

    @FindBy(id = "passengerTv")
    private WebElement passengers;

    @FindBy(id = "luggageTv")
    private WebElement luggage;

    @FindBy(id = "main_screen_clear")
    private WebElement clearTripDetails;


    public Homepage() throws Exception {
        this.driver = getDriver();
       homepageLogger = LoggerFactory.getLogger(this.getClass());
    }

    /***
     * I am going to rip this method in near time
     * @param trip
     * @return
     * @throws Exception
     */
    public Homepage enterTravelDetails(String trip) throws Exception {
        buildTripDetails(trip);
        entity.Location location = tripDetails.getLocation();
        From from = new FromBuilder().withPickupLocation(location.getPickup()).build();
        To to = new ToBuilder().withDropOffLocation(location.getDropoff()).build();
        enterPickup(from);
        boolean viasEntered = pageStore.get(Vias.class).enterVias();
        enterDropoff(to);
        waitForElementToBeInvisible(Bys.LOADER);
        pageStore.get(Popup.class).handleNoCabsAvaliablePopup(this);
        if(viasEntered)
            moveElementToThisElementPos(fromAddress,toAddress);
        waitForElementToBeClickable(dateSelector);
        dateSelector.click();
        this.new SetDate().enterDate(tripDetails.getPickupDate());
        this.new SetTime().enterTime(tripDetails.getPickupTime());
        selectPassengers();
        tripDetails.setWithLuggage(false);
        waitForElementToBeInvisible(Bys.LOADER);
        pageStore.get(Popup.class).handleHiddenCabOperatorDetailsPopup(this);
        return this;
    }

    public Homepage enterTravelDetails(String trip, String luggage) throws Exception {
        this.luggageDetails = luggage;
        enterTravelDetails(JOURNEY_DETALS,trip,luggage);
        return this;
    }

    public Homepage enterTravelDetails(String fileName, String trip, String luggage) throws Exception {
        this.luggageDetails = luggage;
        buildTripDetailsFromFile(trip,fileName);
        switch (tripDetails.getTripType()) {
            case SINGLEJOURNEY:
                enterJourneyTravelDetails(false);
                break;
            case JOURNEYWITHVIA:
                enterJourneyTravelDetails(true);
                break;
            case RETURNJOURNEY:
                enterReturnJourneyTravelDetails(false);
                break;
        }
        return this;
    }

    private Homepage enterJourneyTravelDetails(boolean withVia) throws Exception {
        enterPickupAndDropOff(withVia);
        waitForElementToBeClickable(dateSelector);
        dateSelector.click();
        enterTravelDateAndTime(tripDetails.getPickupDate(),tripDetails.getPickupTime());
        enterPassengersAndLuggage();
        return this;
    }

    private Homepage enterReturnJourneyTravelDetails(boolean withVia) throws Exception {
            enterJourneyTravelDetails(withVia);
            waitForElementToBeClickable(returnDateSelector);
            returnDateSelector.click();
            enterTravelDateAndTime(tripDetails.getReturnDate(),tripDetails.getReturnTime());
            waitForElementToBeInvisible(Bys.LOADER);
        return this;
    }

    private void enterPickupAndDropOff(boolean includeVia) throws Exception {
        entity.Location location = tripDetails.getLocation();
        From from = new FromBuilder().withPickupLocation(location.getPickup()).build();
        To to = new ToBuilder().withDropOffLocation(location.getDropoff()).build();
        enterPickup(from);
        if(includeVia) {
            pageStore.get(Vias.class).enterVias();
            moveElementToThisElementPos(fromAddress,toAddress);
        }
        enterDropoff(to);
        waitForElementToBeInvisible(Bys.LOADER);
        pageStore.get(Popup.class).handleNoCabsAvaliablePopup(this);
    }

    private void enterTravelDateAndTime(Date date, String time) {
        this.new SetDate().enterDate(date);
        this.new SetTime().enterTime(time);
    }

    private void enterPassengersAndLuggage() throws Exception {
        waitForElementToBeInvisible(Bys.LOADER);
        pageStore.get(Popup.class).handleHiddenCabOperatorDetailsPopup(this);
        selectPassengers();
        if(luggageDetails.length()>0) {
            tripDetails.setWithLuggage(true);
            selectLuggage(luggageDetails, tripDetails.getPassengers());
            waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
        }
        waitForElementToBeInvisible(Bys.LOADER);
    }

    private Homepage selectPassengers() throws Exception {
        logger.info("Selecting passengers");
        waitForElementToBeClickable(passengers);
        passengers.click();
        pageStore.get(Passengers.class).chooseNoOfPassengers();
        return this;
    }

    private Homepage selectLuggage(String luggageToSelect, int passengers) throws Exception {
        logger.info("Selecting luggage");
        waitForElementToBeClickable(luggage);
        luggage.click();
        pageStore.get(Popup.class).handleSimilarLuggageSelectionPopup(Luggage.class);
        pageStore.get(Luggage.class).chooseLuggage(luggageToSelect, passengers).verifyLuggagePreview().acceptLuggage();
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
        tripDetails.setLuggage(pageStore.get(Luggage.class).getTotalLuggage());
        return this;
    }

    public PassengerDetails chooseACab(String paymentType) throws Exception {
        PaymentType payment = enumMapper.getPaymentType(paymentType);
        homepageLogger.info(String.format("Choosing a cab with payment type %s",payment.name()));
        switch (tripDetails.getTripType()) {
            case SINGLEJOURNEY:case JOURNEYWITHVIA:
                tripDetails = pageStore.get(CabResultsModule.class).selectACabForSingleJourney(tripDetails,payment);
                tripDetails = pageStore.get(TripDetailsBuilder.class)
                        .withTripTotalPrice(tripDetails.getCab())
                        .build();
                break;
            case RETURNJOURNEY:
                tripDetails = pageStore.get(CabResultsModule.class).selectACabForReturnJourney(tripDetails,payment);
                tripDetails = pageStore.get(TripDetailsBuilder.class)
                        .withTripTotalPrice(tripDetails.getCab(),tripDetails.getReturnCab())
                        .build();
                break;
        }

        return new PassengerDetails();
    }

    public Homepage enterPickup() throws Exception {
        navToEnterPickup();
        if(pageStore.get(pages.minicabitCore.homepageModules.Location.class).isValidLocation(LocationType.PICKUP)) {
            pageStore.get(pages.minicabitCore.homepageModules.Location.class)
                    .enterLocation(new FromBuilder().build())
                    .selectLocation();
        } else {
            throw new LocationException("Unable to navigate to Pickup location");
        }
        return this;
    }

    public Homepage enterPickup(From from) throws Exception {
        navToEnterPickup();
        homepageLogger.info("Entering pickup location as "+from.getPickupLocation());
        if(pageStore.get(pages.minicabitCore.homepageModules.Location.class).isValidLocation(LocationType.PICKUP)) {
            pageStore.get(pages.minicabitCore.homepageModules.Location.class)
                    .enterLocation(from)
                    .selectLocation();
        } else {
            throw new LocationException("Unable to navigate to Pickup location");
        }
        return this;
    }

    public Homepage enterDropoff() throws Exception {
        navToEnterDropOff();
        if(pageStore.get(pages.minicabitCore.homepageModules.Location.class).isValidLocation(LocationType.DROPOFF)) {
            pageStore.get(pages.minicabitCore.homepageModules.Location.class)
                    .enterLocation(new ToBuilder().build())
                    .selectLocation();
        } else {
            throw new LocationException("Unable to navigate to Dropoff location");
        }
        return this;
    }

    public Homepage enterDropoff(To to) throws Exception {
        navToEnterDropOff();
        homepageLogger.info("Entering dropoff location as "+to.getDropOffLocation());
        if(pageStore.get(pages.minicabitCore.homepageModules.Location.class).isValidLocation(LocationType.DROPOFF)) {
            pageStore.get(pages.minicabitCore.homepageModules.Location.class)
                    .enterLocation(to)
                    .selectLocation();
        } else {
            throw new LocationException("Unable to navigate to Dropoff location");
        }
        return this;
    }

    private void navToEnterPickup() {
        waitForElementToBeClickable(fromAddress);
        fromAddress.click();
    }

    private void navToEnterDropOff() {
        waitForElementToBeVisible(toAddress);
        toAddress.click();
    }

    public Location navToLocationPage() {
        logger.info("Navigating to location page...");
        waitForElementToBeClickable(fromAddress);
        fromAddress.click();
        return pageStore.get(Location.class);

    }

    /**
     * Update this code with tesseract logic
     * TODO:
     * @return
     */
    public boolean isHomepage() {
        return home.getText().toUpperCase().equals("FROM");
    }


    public void buildTripDetails(String trip) throws Exception {
        buildTripDetailsFromFile(trip, JOURNEY_DETALS);
    }

    public void buildTripDetailsFromFile(String trip, String fileName) throws Exception {
        File file = new File(fileName);
        String validFileName = null;
        if(!file.exists()) {
            validFileName = getValidDataFilePath(TESTDATA,fileName);
            if(validFileName==null) {
                throw new FileNotFoundException(String.format("Unable to find file %s in folder testdata. Is the file name valid?",fileName+ Constants.Extenstions.JSON));
            }
        } else
            validFileName = fileName;
        JourneyDetails details = (JourneyDetails) dataMapper.mapDetails(JourneyDetails.class, validFileName);
        TripType triptype = enumMapper.getTripType(trip);
        Journey journey = dataFactory.getJourneyType(details, triptype);
        entity.Location location = new entity.Location(journey);
        tripDetails = pageStore.get(TripDetailsBuilder.class)
                .withLocation(location)
                .withJourney(journey)
                .withPickupDate(journey.getPickupDate())
                .withPickupTime(journey.getPickupTime())
                .withPassengers(journey.getPassengers())
                .withTripType(triptype)
                .build();
        if(journey instanceof JourneyWithVia) {
            try {
                tripDetails = pageStore.get(TripDetailsBuilder.class)
                        .withVias(journey.getVias())
                        .build();
            } catch (Exception e) {
                //Ignoring it because we have handled it by using instanceof operator
            }
        } else if( journey instanceof ReturnJourney) {
            try {
                tripDetails = pageStore.get(TripDetailsBuilder.class)
                        .withReturnDate(journey.getReturnDate())
                        .withReturnTime(journey.getReturnTime())
                        .build();
            } catch (Exception e) {
                //Ignoring it because we have handled it by using instanceof operator
            }
        }
    }

    public void clearTripDetails() {
        logger.info("Clearing trip details...");
        if(clearTripDetails==null)
            refreshElements(this);
        clearTripDetails.click();
    }

    public void verifyTripDetailsFor(ClearTrip clearTripStatus) {
        switch (clearTripStatus) {
            case ACCEPTED:
                verifyIfTripDetailsAreCleared();
                break;
            case REJECTED:
                verifyIfTripDetailsAreIntact();
                break;
        }

    }

    public void openHomeMenuAndChoose(HomeMenuOptions homeMenuOption) {
        logger.info(String.format("Choosing home menu option -- %s",homeMenuOption.name()));
        waitForElementToBeClickable(homeBtn);
        homeBtn.click();
        switch (homeMenuOption) {
            case LOGIN:
                pageStore.get(HomeMenu.class).chooseHomeMenuOption(homeMenuOption);
                pageStore.get(PassengerDetails.class).signIn(DataFileStrings.LOGIN_DETAILS);
                break;
            case REGISTER:
                pageStore.get(HomeMenu.class).chooseHomeMenuOption(homeMenuOption);
                pageStore.get(PassengerDetails.class).createNewAccount(DataFileStrings.REGISTRATION_DETAILS);
                pageStore.get(Popup.class).handleVerifyEmailAddressPopup(PassengerDetails.class);
                break;
            case MY_ACCOUNT:
                pageStore.get(HomeMenu.class).chooseHomeMenuOption(homeMenuOption);
        }

    }

    public void openHomeMenuAndChooseMyAccountSubMenu(HomeMenuOptions.MyAccount subMenuOption) {
        logger.info(String.format("Choosing home menu option -- %s",HomeMenuOptions.MY_ACCOUNT.name()));
        waitForElementToBeClickable(homeBtn);
        homeBtn.click();
        pageStore.get(HomeMenu.class).chooseHomeMenuOptionWithMyAccountSubMenu(subMenuOption);
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
    }

    public void openHomeMenuAndChoose(HomeMenuOptions homeMenuOption, HomeMenuOptions.ContactUs subMenuOption) {
        logger.info(String.format("Choosing home menu option -- %s",homeMenuOption.name()));
        waitForElementToBeClickable(homeBtn);
        homeBtn.click();
        //TODO

    }


    public void openHomeMenuAndNavigate(HomeMenuOptions homeMenuOption) {
        logger.info(String.format("Choosing home menu option -- %s",homeMenuOption.name()));
        waitForElementToBeClickable(homeBtn);
        homeBtn.click();
        switch (homeMenuOption) {
            case LOGIN:
            case REGISTER:
                pageStore.get(HomeMenu.class).chooseHomeMenuOption(homeMenuOption);
                break;
        }

    }

    private void verifyIfTripDetailsAreIntact() {
        assertEquals(String.format("Pickup address %s does not contain " +
                "the address selected by user %s", fromAddress.getText(),
                tripDetails.getLocation().getPickup()),
                fromAddress.getText().contains(tripDetails.getLocation().getPickup()),true);
    }

    private void verifyIfTripDetailsAreCleared() {
        assertEquals(String.format("Pickup address does not have placeholder value %s ",LOCATION_PLACEHOLDER),
                fromAddress.getText(),LOCATION_PLACEHOLDER);
    }

    private void setValue(AndroidElement element, String value) {
        element.replaceValue(value);
    }

    protected class SetDate {
        @FindBy(id = "numberpicker_input")
        private List<AndroidElement> dateFields;

        @FindBy(id = "date_picker_set_btn")
        private WebElement set_btn;

        @FindBy(id = "date_picker_cancel_btn")
        private WebElement cancel_btn;

        private boolean dateSet;

        public SetDate() {
            PageFactory.initElements(driver,this);
        }

        public Homepage enterDate(Date date) {
            waitForElementToBeClickable(set_btn);
            DatePicker picker = pageStore.get(DatePicker.class).setDate(date);
            setDate(picker);
            set_btn.click();
            return Homepage.this;
        }

        private void setDate(DatePicker picker) {
            while (!dateSet) {
                dateFields = driver.findElements(By.id("numberpicker_input"));
                if(!isDayField(dateFields.get(0).getText())) {
                    homepageLogger.info(String.format("Entering pickup date as %s-%s-%s",picker.getMonth(),picker.getDay(),picker.getYear()));
                    setValue(dateFields.get(0),picker.getMonth());
                    setValue(dateFields.get(1),picker.getDay());
                    setValue(dateFields.get(2),picker.getYear());
                    if(isMonthSame(dateFields.get(0).getText(), picker)
                            && isDaySame(dateFields.get(1).getText(),picker)
                            && isYearSame(dateFields.get(2).getText(),picker)) {
                        dateSet = true;
                    } else {
                        setDate(picker);
                    }
                } else {
                    homepageLogger.info(String.format("Entering pickup date as %s-%s-%s",picker.getDay(),picker.getMonth(),picker.getYear()));
                    setValue(dateFields.get(0),picker.getDay());
                    setValue(dateFields.get(1),picker.getMonth());
                    setValue(dateFields.get(2),picker.getYear());
                    if(isMonthSame(dateFields.get(1).getText(), picker)
                            && isDaySame(dateFields.get(0).getText(),picker)
                            && isYearSame(dateFields.get(2).getText(),picker)) {
                        dateSet = true;
                    } else {
                        setDate(picker);
                    }
                }
                if(dateSet) {
                    break;
                }
            }
        }

        private boolean isMonthSame(String expMonth, DatePicker picker) {
            return expMonth.equals(picker.getMonth());
        }

        private boolean isDaySame(String expDay, DatePicker picker) {
            return expDay.equals(picker.getDay());
        }

        private boolean isYearSame(String expYear, DatePicker picker) {
            return expYear.equals(picker.getYear());
        }

        private boolean isDayField(String text) {
            boolean isDayField = false;
            try{
                Integer.parseInt(text);
                if(text.length()==2) {
                    isDayField = true;
                }
            } catch (Exception e) {

            }

            return isDayField;
        }

    }

    protected class SetTime {

        @FindBy(id = "numberpicker_input")
        private List<WebElement> timeFields;

        @FindBy(id = "time_picker_set_btn")
        private WebElement set_btn;

        @FindBy(id = "time_picker_cancel_btn")
        private WebElement cancel_btn;

        @FindBy(xpath = "//android.widget.NumberPicker[1]//android.widget.EditText")
        private WebElement hourInput;

        @FindBy(xpath = "//android.widget.NumberPicker[2]//android.widget.EditText")
        private WebElement minInput;

        public SetTime() {
            PageFactory.initElements(driver,this);
        }

        private String currentHour,currentMin, requiredHour, requiredMin;

        public Homepage enterTime(String time) {
            waitForElementToBeClickable(set_btn);
            TimePicker timePicker = new TimePicker(time);
            homepageLogger.info(String.format("Entering pickup time as %s:%s",timePicker.getHours(),timePicker.getMinutes()));
            boolean timeSet = selectTime(timePicker);
            if(timeSet) {
                set_btn.click();
            } else {
                selectTime(timePicker);
                set_btn.click();
            }
            return Homepage.this;
        }

        private boolean selectTime(TimePicker timePicker) {
            boolean timeSet =false;
            currentHour = hourInput.getText();
            currentMin = minInput.getText();
            requiredHour = timePicker.getHours();
            requiredMin = timePicker.getMinutes();
            int hourDiff = NumberUtils.toInt(requiredHour) - NumberUtils.toInt(currentHour);
            int minutesDiff = NumberUtils.toInt(requiredMin) - NumberUtils.toInt(currentMin);
            while (!timeSet) {
                keepTimeGoing(hourDiff,minutesDiff);
                currentHour = hourInput.getText();
                currentMin = minInput.getText();
                hourDiff = NumberUtils.toInt(requiredHour) - NumberUtils.toInt(currentHour);
                minutesDiff = NumberUtils.toInt(requiredMin) - NumberUtils.toInt(currentMin);
                if(hourDiff==0 && minutesDiff==0)
                    timeSet = true;
            }
            return timeSet;
        }

        private void keepTimeGoing(int hourDiff, int minutesDiff) {
            if(hourDiff>0) {
                gotoNextHourBy(hourDiff);
            } else {
                gotoPrevHourBy(Math.abs(hourDiff));
            }
            if(minutesDiff>0) {
                gotoNextMinuteBy(minutesDiff);
            } else {
                gotoPrevMinuteBy(Math.abs(minutesDiff));
            }
        }

        private void gotoPrevHourBy(int diff) {
             MobileElement prevHour_btn = (MobileElement) driver.findElement(By.xpath("//android.widget.NumberPicker[1]//android.widget.Button[@index='0']"));
            keepHoursGoing(prevHour_btn,diff);
        }

        private void gotoNextHourBy(int diff) {
            MobileElement nextHour_btn = (MobileElement) driver.findElement(By.xpath("//android.widget.NumberPicker[1]//android.widget.Button[@index='2']"));
            keepHoursGoing(nextHour_btn,diff);
        }

        private void gotoPrevMinuteBy(int diff) {
           MobileElement prevMin_btn = (MobileElement) driver.findElement(By.xpath("//android.widget.NumberPicker[2]//android.widget.Button[@index='0']"));
            keepMinutesGoing(prevMin_btn,diff);
        }

        private void gotoNextMinuteBy(int diff) {
            MobileElement nextMin_btn = (MobileElement) driver.findElement(By.xpath("//android.widget.NumberPicker[2]//android.widget.Button[@index='2']"));
            keepMinutesGoing(nextMin_btn,diff);
        }

        private void keepHoursGoing(MobileElement element, int times) {
            for(int i=0; i<times; i++) {
                element.tap(1,0);
            }
        }

        private void keepMinutesGoing(MobileElement element, int times) {
            for(int i=0; i<times/10; i++) {
                element.tap(1,0);
            }
        }
    }
}
