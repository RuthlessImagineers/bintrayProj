package steps.minicabitCore;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.Popup;
import pages.minicabitCore.PassengerDetails;
import steps.BaseSteps;
import static org.junit.Assert.*;
import static enums.UserType.*;

import utils.Constants;

import java.util.List;

public class PassengerDetailspageSteps extends BaseSteps {

    @And("^On PassengerDetails page I create a new passenger with details (\\w+)$")
    public void onPassengerDetailsPageICreateANewPassengerWithDetailsRegisterDetails(String passengerDetails) throws Throwable {
            pageStore.get(PassengerDetails.class).createNewAccount(passengerDetails);
            String popupMessage = pageStore.get(Popup.class).getPopupMessage();
            assertEquals(popupMessage, Constants.PopupMessages.VERIFY_EMAIL);
            pageStore.get(Popup.class).handleVerifyEmailAddressPopup(pageStore.get(PassengerDetails.class));

    }

    @And("^On PassengerDetails page I enter passenger login details (\\w+)$")
    public void onPassengerDetailsPageIEnterPassengerLoginDetailsLoginDetails(String loginDetails) throws Throwable {
        pageStore.get(PassengerDetails.class).signIn(loginDetails);
    }

    @When("^On PassengerDetails page I try to register a user without entering details$")
    public void onPassengerDetailsPageITryToRegisterAUserWithoutEnteringAnyDetails() throws Throwable {
        pageStore.get(PassengerDetails.class).navigateToPage(GUEST)
                .tryCreatingAccountWithMissingDetails();
    }

    @Then("^On PassengerDetails page I should be receive a suggestion of missing fields$")
    public void onPassengerDetailsPageIShouldBeReceiveASuggestionOfMissingFields() throws Throwable {
        pageStore.get(PassengerDetails.class).verifyAllMandatoryFields();
    }

    @When("^On PassengerDetails page I try to register a user without entering (\\w+) detail$")
    public void onPassengerDetailsPageITryToRegisterAUserWithoutEnteringMandatoryFieldDetail(String mandatoryField) throws Throwable {
        pageStore.get(PassengerDetails.class).navigateToPage(GUEST)
                .tryCreatingAccountWithMissingDetails(mandatoryField);
    }

    @Then("^On PassengerDetails page I should be receive a suggestion of missing field (\\w+)$")
    public void onPassengerDetailsPageIShouldBeReceiveASuggestionOfMissingFieldMandatoryField(String mandatoryField) throws Throwable {
        pageStore.get(PassengerDetails.class).verifyMandatoryField(mandatoryField);
    }

    @When("^On PassengerDetails page I create a new passenger with existing details$")
    public void onPassengerDetailsPageICreateANewPassengerWithExistingDetails() throws Throwable {
        pageStore.get(PassengerDetails.class).createAccountForExistingUser();
    }

    @Then("^On PassengerDetails page I should receive a warning of user registration$")
    public void onPassengerDetailsPageIShouldReceiveAWarningOfUserRegistration() throws Throwable {
        pageStore.get(Popup.class).handleRegisterEmailExistsPopup(PassengerDetails.class);
    }

    @Then("^On PassengerDetails page I should receive a successful message$")
    public void onPassengerDetailsPageIShouldReceiveASuccessfulMessage() throws Throwable {
        pageStore.get(Popup.class).handleVerifyEmailAddressPopup(PassengerDetails.class);
    }

    @When("^On PassengerDetails page I create a new passenger with details \"([^\"]*)\"$")
    public void onPassengerDetailsPageICreateANewPassengerWithDetails(String registrationDetials) throws Throwable {
        pageStore.get(PassengerDetails.class).createNewAccount(registrationDetials);
    }
}
