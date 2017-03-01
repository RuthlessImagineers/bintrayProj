package pages.minicabitCore;

import builders.BookingBuilder;
import entity.Booking;
import entity.TripDetails;
import entity.TripSummary;
import enums.BookingHistory;
import enums.HomeMenuOptions;
import exceptions.MyBookingsException;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.ArrayList;
import java.util.List;

public class MyBookingspage extends BasePage {

    @FindBy(xpath = "//android.widget.TextView[@text=\"Past bookings\"]")
    private WebElement pastBookings;

    @FindBy(xpath = "//android.widget.TextView[@text=\"Future bookings\"]")
    private WebElement futureBookings;

    @FindBy(xpath = "//android.widget.ListView/android.widget.FrameLayout")
    private List<WebElement> bookings;

    private AppiumDriver driver;
    private Logger myBookingsLogger;
    private BookingHistory bookingHistory;
    private List<String> tripDetailsList = new ArrayList<>();
    private List<String> bookingDetailsList = new ArrayList<>();

    private entity.Booking booking;
    private TripDetails tripDetails;
    private TripSummary tripSummary;
    public MyBookingspage() throws Exception {
            myBookingsLogger = LoggerFactory.getLogger(this.getClass());
            tripDetails = pageStore.get(TripDetails.class);
            tripSummary = pageStore.get(TripSummary.class);
    }

    public MyBookingspage navigateToBooking(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
        switch (bookingHistory) {
            case FUTURE_BOOKINGS:
                navTo(futureBookings);
                myBookingsLogger.info("Navigated to future bookings tab");
                break;
            case PAST_BOOKINGS:
                navTo(pastBookings);
                myBookingsLogger.info("Navigated to past bookings tab");
                break;
        }
        return this;
    }


    private void navTo(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    public MyBookingspage verifyBooking() throws MyBookingsException {
        switch (bookingHistory) {
            case FUTURE_BOOKINGS:
                verifyForABookingInFutureBookings();
                break;
            case PAST_BOOKINGS:
                verifyForABookingInPastBookings();
                break;
        }
        return this;
    }


    private MyBookingspage verifyForABookingInFutureBookings() throws MyBookingsException {
        if(bookings.size()==0) {
            throw new MyBookingsException("Registered user bookings are not saved in future bookings");
        }
        for(WebElement bookingEle : bookings) {
            booking = this.new Booking(bookingEle).buildABooking();
            boolean bookingVerified = verifyBookingDetails();
            if(bookingVerified) {
                break;
            }

        }
        return this;
    }

    private MyBookingspage verifyForABookingInPastBookings() {

        return this;
    }

    private boolean verifyBookingDetails() {
        boolean bookingVerified = false;
        buildTripDetailsList();
        buildBookingDetailsList();
        if(tripDetailsList.equals(bookingDetailsList)) {
            bookingVerified = true;
        }
        return bookingVerified;
    }

    private void buildTripDetailsList() {
        tripDetailsList.add(tripDetails.getTotalPrice());
        tripDetailsList.add(tripDetails.getLocation().getPickup());
        tripDetailsList.add(tripDetails.getLocation().getDropoff());
        tripDetailsList.add(tripDetails.getCab().getCabProviderBase());
        tripDetailsList.add(tripDetails.getCab().getCabProviderEmission());
        tripDetailsList.add(tripSummary.getPickupDate()+" "+tripSummary.getPickupTime());
        tripDetailsList.add(tripSummary.getCabProviderName());
    }

    private void buildBookingDetailsList() {
        bookingDetailsList.add(booking.getBookedPrice());
        bookingDetailsList.add(booking.getBookedTitle());
        bookingDetailsList.add(booking.getBookedFrom());
        bookingDetailsList.add(booking.getBookedTo());
        bookingDetailsList.add(booking.getBookedEmission());
        bookingDetailsList.add(booking.getBookedTripBase());
        bookingDetailsList.add(booking.getBookedDate());
    }

    private class Booking {

        private By bookedTripTitle = By.id("booked_trip_row_title_tv");
        private By bookedTripPrice = By.id("booked_trip_row_price_tv");
        private By bookedTripFrom = By.id("booked_trip_row_from");
        private By bookedTripTo = By.id("booked_trip_row_to");
        private By bookedTripBase = By.id("booked_trip_based_tv");
        private By bookedTripEmission = By.id("booked_trip_emission_tv");
        private By bookedDate = By.id("booked_trip_row_pick_up_date");
        private WebElement rootElement;

        public Booking(WebElement rootElement) {
            refreshElements(this);
            this.rootElement = rootElement;
        }

        public entity.Booking buildABooking() {
           entity.Booking booking =  pageStore.get(BookingBuilder.class)
                    .withBookedTitle(getBookedTripTitle())
                    .withBookedPrice(getBookedTripPrice())
                    .withBookedFrom(getBookedTripFrom())
                    .withBookedTo(getBookedTripTo())
                    .withBookedTripBase(getBookedTripBase())
                    .withBookedEmission(getBookedTripEmission())
                    .withBookedDate(getBookingDate())
                    .build();
            return booking;
        }

        private String getBookedTripTitle() {
            WebElement tripTitleElement = rootElement.findElement(bookedTripTitle);
            return tripTitleElement.getText();
        }

        private String getBookedTripPrice() {
            WebElement bookedTripPriceElement = rootElement.findElement(bookedTripPrice);
            return bookedTripPriceElement.getText();
        }

        private String getBookedTripFrom() {
            WebElement bookedTripFromElement = rootElement.findElement(bookedTripFrom);
            return bookedTripFromElement.getText();
        }

        private String getBookedTripTo() {
            WebElement bookedTripToElement = rootElement.findElement(bookedTripTo);
            return bookedTripToElement.getText();
        }

        private String getBookedTripBase() {
            WebElement bookedTripBaseElement = rootElement.findElement(bookedTripBase);
            return bookedTripBaseElement.getText();
        }

        private String getBookedTripEmission() {
            WebElement bookedTripEmissionElement = rootElement.findElement(bookedTripEmission);
            return bookedTripEmissionElement.getText();
        }

        private String getBookingDate() {
            WebElement bookedTripDateElement = rootElement.findElement(bookedDate);
            return bookedTripDateElement.getText();
        }


    }


}
