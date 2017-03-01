package steps.minicabitCore;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import pages.minicabitCore.TripConfirmationPage;
import steps.BaseSteps;

public class TripConfirmationpageSteps extends BaseSteps {


    @Then("^On Trip confirmation page I should receive a booking confirmation reference Id$")
    public void onTripConfirmationPageIShouldReceiveABookingConfirmationReferenceId() throws Throwable {
            pageStore.get(TripConfirmationPage.class)
                     .verifyTripDetails();
            pageStore.get(TripConfirmationPage.class).closeTripConfirmationPage();

    }
}
