package steps.minicabitCore;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import enums.BookingHistory;
import pages.minicabitCore.MyBookingspage;
import steps.BaseSteps;

import static enums.BookingHistory.*;

public class MyBookingsPageSteps extends BaseSteps {

    @Then("^On MyBookings page my latest booking should be present$")
    public void onMyBookingsPageMyLatestBookingShouldBePresent() throws Throwable {
        pageStore.get(MyBookingspage.class).navigateToBooking(FUTURE_BOOKINGS)
                .verifyBooking();
    }
}
