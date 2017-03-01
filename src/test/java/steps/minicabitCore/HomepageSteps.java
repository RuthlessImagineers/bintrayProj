package steps.minicabitCore;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static enums.ClearTrip.*;

import pages.Popup;
import pages.minicabitCore.Homepage;
import steps.BaseSteps;

import static utils.Constants.*;
import static enums.HomeMenuOptions.*;

public class HomepageSteps extends BaseSteps {



    @Given("^On Homepage I enter trip details for trip type (\\w+)$")
    public void onHomepageIEnterTripDetailsForTripType(String trip) throws Throwable {
       pageStore.get(Homepage.class).enterTravelDetails(trip);
    }


    @And("^On Homepage I choose a cab of payment type (\\w+)$")
    public void onHomepageIChooseACabOfPaymentType(String paymentType) throws Throwable {
       pageStore.get(Homepage.class).chooseACab(paymentType);
    }



    @Given("^On Homepage I enter trip details for trip type (\\w+) and with luggage (.*)$")
    public void onHomepageIEnterTripDetailsForTripTypeAndWithLuggage(String trip, String luggage) throws Throwable {
        pageStore.get(Homepage.class).enterTravelDetails(trip,luggage);
    }


    @Given("^On Homepage I enter trip details$")
    public void onHomepageIEnterTripDetails() throws Throwable {
        pageStore.get(Homepage.class).enterTravelDetails(DEFAULT_BOOKING);
    }

    @When("^On Homepage I clear the trip details$")
    public void onHomepageIClearTheTripDetails() throws Throwable {
        pageStore.get(Homepage.class).clearTripDetails();
    }

    @Then("^On Hompage the system seeks my approval for clearing details$")
    public void onHompageTheSystemSeeksMyApprovalForClearingDetails() throws Throwable {
        pageStore.get(Popup.class).verifyIfClearTripDetailsPopupIsPresent();
    }

    @When("^On Homepage I accept clearing trip details$")
    public void onHomepageIAcceptClearingTripDetails() throws Throwable {
        pageStore.get(Popup.class).handleClearTripDetailsPopup(true,Homepage.class);
    }

    @Then("^On Homepage my trip details should get cleared$")
    public void onHomepageMyTripDetailsShouldGetCleared() throws Throwable {
       pageStore.get(Homepage.class).verifyTripDetailsFor(ACCEPTED);
    }

    @When("^On Homepage I reject clearing trip details$")
    public void onHomepageIRejectClearingTripDetails() throws Throwable {
        pageStore.get(Popup.class).handleClearTripDetailsPopup(false,Homepage.class);
    }

    @Then("^On Homepage my trip details should be intact$")
    public void onHomepageMyTripDetailsShouldBeIntact() throws Throwable {
        pageStore.get(Homepage.class).verifyTripDetailsFor(REJECTED);
    }

    @And("^On Homepage I clear the trip details again$")
    public void onHomepageIClearTheTripDetailsAgain() throws Throwable {
        pageStore.get(Homepage.class).clearTripDetails();
    }

    @Given("^On Homepage I login as registered user$")
    public void onHomepageILoginAsRegisteredUser() throws Throwable {
        pageStore.get(Homepage.class).openHomeMenuAndChoose(LOGIN);
    }

    @And("^On Homepage I navigate to location page$")
    public void onHomepageINavigateToLocationPage() throws Throwable {
        pageStore.get(Homepage.class).navToLocationPage();
    }


    @Given("^On Homepage I enter trip details from (\\w+) for trip type (\\w+) and with luggage (\\w+)$")
    public void onHomepageIEnterTripDetailsFromTripDataForTripTypeTripDetailsAndWithLuggageAdditionalLuggage(String tripDataFileName, String tripDetails, String additionalLuggage) throws Throwable {
        pageStore.get(Homepage.class).enterTravelDetails(tripDataFileName,tripDetails,additionalLuggage);
    }

    @Given("^On Homepage I choose to register$")
    public void onHomepageIChooseToRegister() throws Throwable {
        pageStore.get(Homepage.class)
                .openHomeMenuAndChoose(REGISTER);
    }

    @Given("^From Homepage I navigate to passenger details page to create a new account$")
    public void fromHomepageINavigateToPassengerDetailsPageToCreateANewAccount() throws Throwable {
        pageStore.get(Homepage.class)
                .openHomeMenuAndNavigate(REGISTER);
    }

    @Given("^On Homepage I choose to go to registration page$")
    public void onHomepageIChooseToGoToRegistrationPage() throws Throwable {
        pageStore.get(Homepage.class)
                .openHomeMenuAndNavigate(REGISTER);
    }

    @And("^On Homepage I navigate to my bookings$")
    public void onHomepageINavigateToMyBookings() throws Throwable {
        pageStore.get(Homepage.class).openHomeMenuAndChooseMyAccountSubMenu(MyAccount.MY_BOOKINGS);
    }
}