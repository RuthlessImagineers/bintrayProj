package steps.minicabitCore;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import pages.minicabitCore.TripSummaryPageModules.*;
import steps.BaseSteps;


public class TripSummarypageSteps extends BaseSteps {

    /**
     * Change in UI overhaul on android
     * @throws Throwable
     */
    @Deprecated
    @And("^On TripSummary page  I verify my trip details$")
    public void onTripSummaryPageIVerifyMyTripDetails() throws Throwable {
           /* pageStore.get(TripSummaryPage.class).verifyTripDetails()
                    .navToPayments();*/
    }

    @And("^On TripSummary page I select payment method$")
    public void onTripSummaryPageISelectPaymentMethod() throws Throwable {
        pageStore.get(TripSummaryPage.class).navToPayments();
    }

    @And("^On TripSummary page I enter flight details as (\\w+)$")
    public void onTripSummaryPageIEnterFlightDetailsAsFlightNumber(String flightNumber) throws Throwable {

    }

    @And("^On TripSummary page I enter flight details$")
    public void onTripSummaryPageIEnterFlightDetails() throws Throwable {
        pageStore.get(TripSummaryPage.class).enterFlightNumber();
    }

    @And("^On TripSummary page I enter flight details and confirm booking$")
    public void onTripSummaryPageIEnterFlightDetailsAndConfirmBooking() throws Throwable {
        pageStore.get(TripSummaryPage.class).enterFlightNumber()
                                    .confirmBooking();

    }
}
