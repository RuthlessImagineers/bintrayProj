package pages.minicabitCore;

import entity.WarningPopups;
import entity.passengerDetails.ContactInformation;
import entity.passengerDetails.YourDetails;
import enums.*;
import exceptions.UnsupportedMandatoryFieldException;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.Popup;
import pages.minicabitCore.TripSummaryPageModules.TripSummaryPage;
import utils.Constants;
import utils.locatorUtils.Bys;
import utils.DriverFactory;

import static utils.Constants.FolderLocations.*;
import static utils.Constants.Extenstions.*;
import static utils.Constants.MandatoryFieldsMessages.*;
import static utils.Constants.DataFileStrings.*;
import static org.junit.Assert.*;



public class PassengerDetails extends BasePage {

    @FindBy(id = "view_tab_text_tab2")
    private WebElement signInTab;

    @FindBy(id = "view_tab_text_tab1")
    private WebElement createAccountTab;

    private AppiumDriver driver;
    private entity.passengerDetails.SignIn signInDetails;
    private entity.passengerDetails.CreateAccount newPassengerDetails;

    public PassengerDetails() throws Exception {
        driver = DriverFactory.getDriver();
        newPassengerDetails = (entity.passengerDetails.CreateAccount) dataMapper.mapDetails(entity.passengerDetails.CreateAccount.class,TESTDATA+ Constants.DataFileStrings.REGISTRATION_DETAILS+JSON);
    }

    public TripSummaryPage signIn(String loginDetails) {
        String filePath = getValidDataFilePath(TESTDATA,loginDetails);
        signInDetails = (entity.passengerDetails.SignIn) dataMapper.mapDetails(entity.passengerDetails.SignIn.class, filePath);
        pageStore.get(entity.PassengerDetails.class).setSignIn(signInDetails);
        waitForElementToBeClickable(signInTab);
        signInTab.click();
        entity.PassengerDetails passengerSignInDetails = pageStore.get(entity.PassengerDetails.class);
        this.new SignIn().signInAs(passengerSignInDetails.getSignIn());
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
        return pageStore.get(TripSummaryPage.class);
    }

    public PassengerDetails createNewAccount(String accountDetails) {
        String filePath = getValidDataFilePath(TESTDATA,accountDetails);
        newPassengerDetails = (entity.passengerDetails.CreateAccount) dataMapper.mapDetails(entity.passengerDetails.CreateAccount.class,filePath);
        logger.info("Creating new user account with details as...");
        pageStore.get(entity.PassengerDetails.class).setCreateAccount(newPassengerDetails);
        waitForElementToBeClickable(createAccountTab);
        createAccountTab.click();
        entity.PassengerDetails passengerCreateAccountDetails = pageStore.get(entity.PassengerDetails.class);
        this.new CreateAccount().fillYourDetails(passengerCreateAccountDetails.getCreateAccount().getYourDetails())
                .fillContactInformation(passengerCreateAccountDetails.getCreateAccount().getContactInformation())
                .createAccount();
        return this;
    }

    public PassengerDetails createAccountForExistingUser() {
        logger.info("Creating a new user with existing details");
        createNewAccount(EXISTING_USER_DETAILS);
        return this;
    }

    public PassengerDetails navigateToPage(UserType userType) {
        switch (userType) {
            case GUEST:
                navigateToRegistrationPage();
                break;
            case REGISTERED:
                navigateToSignInPage();
                break;
        }
        return this;
    }

    private void navigateToRegistrationPage() {
        waitForElementToBeClickable(createAccountTab);
        createAccountTab.click();
    }

    private void navigateToSignInPage() {
        waitForElementToBeClickable(signInTab);
        signInTab.click();
    }


    public PassengerDetails tryCreatingAccountWithMissingDetails() {
        WarningPopups warningPopups = pageStore.get(WarningPopups.class);
        logger.info("Trying to create an account without entering any details");
        this.new CreateAccount().createAccount();
        boolean isTitleWarningMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_TITLE,this);
        warningPopups.setTitleWarning(isTitleWarningMissing);
        logger.info("Trying to create an account without entering firstName");
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        this.new CreateAccount().createAccount();
        boolean isFirstNameMissing =  pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_FIRST_NAME,this);
        warningPopups.setFirstNameWarning(isFirstNameMissing);
        logger.info("Trying to create an account without entering lastName");
        this.new CreateAccount().enterFirstName(newPassengerDetails.getYourDetails().getFirstName());
        this.new CreateAccount().createAccount();
        boolean isLastNameMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_LAST_NAME,this);
        warningPopups.setLastNameWarning(isLastNameMissing);
        logger.info("Trying to create an account without entering email");
        this.new CreateAccount().enterLastName(newPassengerDetails.getYourDetails().getLastName());
        this.new CreateAccount().createAccount();
        boolean isEmailMissing = handleMissingDetailsText(MISSING_EMAIL);
        warningPopups.setEmailWarning(isEmailMissing);
        logger.info("Trying to create an account without entering mobileNumber");
        this.new CreateAccount().enterEmail(newPassengerDetails.getContactInformation().getEmail());
        this.new CreateAccount().createAccount();
        boolean isMobileNumberMissing = handleMissingDetailsText(MISSING_MOBILE_NUMBER);
        warningPopups.setMobileNumberWarning(isMobileNumberMissing);
        logger.info("Trying to create an account without password");
        this.new CreateAccount().enterMobileNumber(newPassengerDetails.getContactInformation().getMobileNumber());
        this.new CreateAccount().createAccount();
        boolean isPasswordMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_PASSWORD,this);
        warningPopups.setPasswordWarning(isPasswordMissing);
        logger.info("Trying to create an account without confirming password");
        this.new CreateAccount().enterPassword(newPassengerDetails.getContactInformation().getPassword());
        this.new CreateAccount().createAccount();
        boolean isConfirmPasswordMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_CONFIRM_PASSWORD,this);
        warningPopups.setConfirmPasswordWarning(isConfirmPasswordMissing);
        return this;
    }

    public PassengerDetails tryCreatingAccountWithMissingDetails(String mandatoryFieldString) throws UnsupportedMandatoryFieldException {
        MandatoryFields mandatoryField = enumMapper.getMandatoryFieldType(mandatoryFieldString);
        switch (mandatoryField) {
            case TITLE: missTitle();
                break;
            case FIRST_NAME: missFirstName();
                break;
            case LAST_NAME: missLastName();
                break;
            case EMAIL: missEmail();
                break;
            case MOBILE_NUMBER: missMobileNumber();
                break;
            case PASSWORD: missPassword();
                break;
            case CONFIRM_PASSWORD: missConfirmPassword();
                break;
        }
        return this;
    }

    public PassengerDetails verifyMandatoryField(String mandatoryField) throws UnsupportedMandatoryFieldException {
        MandatoryFields mandatoryFields = enumMapper.getMandatoryFieldType(mandatoryField);
        this.new Verification(mandatoryFields).verifyIfWarningIsPresent();
        return this;
    }

    public PassengerDetails verifyAllMandatoryFields() throws UnsupportedMandatoryFieldException {
        this.new Verification().verifyAllWarnings();
        return this;
    }

    private void missTitle() {
        logger.info("Trying to create an account without entering any details");
        this.new CreateAccount().createAccount();
        boolean isTitleMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_TITLE,this);
        pageStore.get(WarningPopups.class).setTitleWarning(isTitleMissing);
    }

    private void missFirstName() {
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        logger.info("Trying to create an account without entering firstName");
        this.new CreateAccount().createAccount();
        boolean isFirstNameMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_FIRST_NAME,this);
        pageStore.get(WarningPopups.class).setFirstNameWarning(isFirstNameMissing);
    }

    private void missLastName() {
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        this.new CreateAccount().enterFirstName(newPassengerDetails.getYourDetails().getFirstName());
        logger.info("Trying to create an account without entering lastName");
        this.new CreateAccount().createAccount();
        boolean isLastNameMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_LAST_NAME,this);
        pageStore.get(WarningPopups.class).setLastNameWarning(isLastNameMissing);
    }

    private void missEmail() {
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        this.new CreateAccount().enterFirstName(newPassengerDetails.getYourDetails().getFirstName());
        this.new CreateAccount().enterLastName(newPassengerDetails.getYourDetails().getLastName());
        logger.info("Trying to create an account without entering email");
        this.new CreateAccount().createAccount();
        boolean isEmailMissing = handleMissingDetailsText(MISSING_EMAIL);
        pageStore.get(WarningPopups.class).setEmailWarning(isEmailMissing);
    }

    private void missMobileNumber() {
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        this.new CreateAccount().enterFirstName(newPassengerDetails.getYourDetails().getFirstName());
        this.new CreateAccount().enterLastName(newPassengerDetails.getYourDetails().getLastName());
        this.new CreateAccount().enterEmail(newPassengerDetails.getContactInformation().getEmail());
        logger.info("Trying to create an account without entering mobileNumber");
        this.new CreateAccount().createAccount();
        boolean isMobileNumberMissing = handleMissingDetailsText(MISSING_MOBILE_NUMBER);
        pageStore.get(WarningPopups.class).setMobileNumberWarning(isMobileNumberMissing);
    }

    private void missPassword() {
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        this.new CreateAccount().enterFirstName(newPassengerDetails.getYourDetails().getFirstName());
        this.new CreateAccount().enterLastName(newPassengerDetails.getYourDetails().getLastName());
        this.new CreateAccount().enterEmail(newPassengerDetails.getContactInformation().getEmail());
        this.new CreateAccount().enterMobileNumber(newPassengerDetails.getContactInformation().getMobileNumber());
        logger.info("Trying to create an account without password");
        this.new CreateAccount().createAccount();
        boolean isPasswordMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_PASSWORD,this);
        pageStore.get(WarningPopups.class).setPasswordWarning(isPasswordMissing);
    }

    private void missConfirmPassword() {
        this.new CreateAccount().selectTitle(newPassengerDetails.getYourDetails().getTitle());
        this.new CreateAccount().enterFirstName(newPassengerDetails.getYourDetails().getFirstName());
        this.new CreateAccount().enterLastName(newPassengerDetails.getYourDetails().getLastName());
        this.new CreateAccount().enterEmail(newPassengerDetails.getContactInformation().getEmail());
        this.new CreateAccount().enterMobileNumber(newPassengerDetails.getContactInformation().getMobileNumber());
        this.new CreateAccount().enterPassword(newPassengerDetails.getContactInformation().getPassword());
        logger.info("Trying to create an account without confirming password");
        this.new CreateAccount().createAccount();
        boolean isConfirmPasswordMissing = pageStore.get(Popup.class).handleSingleButtonPopup(MISSING_CONFIRM_PASSWORD,this);
        pageStore.get(WarningPopups.class).setConfirmPasswordWarning(isConfirmPasswordMissing);
    }

    private boolean handleMissingDetailsText(String message) {
        WebElement warningElement = driver.findElement(Bys.MISSING_VALUE);
        Assert.assertEquals(String.format("Expected message is %s while the one showed is %s",message,warningElement.getText()),message,warningElement.getText());
        return true;
    }



    private class SignIn {

        @FindBy(id = "login_name")
        private WebElement emailAddress;

        @FindBy(id = "login_password")
        private WebElement password;

        @FindBy(id = "login_login_btn")
        private WebElement signInBtn;

        @FindBy(id = "login_remember_my_email")
        private WebElement chckbxRememberMyEmail;

        @FindBy(id = "login_forgot_pass_tv")
        private WebElement lnkForgotPassword;

        public SignIn() {
            refreshElements(this);
        }
        public void signInAs(entity.passengerDetails.SignIn signInDetails) {
            signInAs(signInDetails,true);
        }

        public void signInAs(entity.passengerDetails.SignIn signInDetails, boolean rememberMe) {
            waitForElementToBeClickable(signInBtn);
            logger.info("------------------------Signing in as----------------------------");
            logger.info(String.format("Email Address --- %s",signInDetails.getUserName()));
            enterValue(emailAddress,signInDetails.getUserName());
            logger.info(String.format("Password --- %s",signInDetails.getPassword()));
            enterValue(password,signInDetails.getPassword());
            driver.hideKeyboard();
            waitForElementToBeVisible(signInBtn);
            if(getStateForAttribute(chckbxRememberMyEmail, Attribute.CHECKED)!=rememberMe)
                chckbxRememberMyEmail.click();
            signInBtn.click();
        }
    }

    private class CreateAccount {

        @FindBy(id = "details_title")
        private WebElement title;

        @FindBy(xpath = "//android.widget.EditText[@text=\"First Name*\"]")
        private WebElement firstName;

        @FindBy(xpath = "//android.widget.EditText[@text=\"Last Name*\"]")
        private WebElement lastName;

        @FindBy(xpath = "//android.widget.EditText[@text=\"Email*\"]")
        private WebElement email;

        @FindBy(xpath = "//android.widget.EditText[@text=\"Mobile Number*\"]")
        private WebElement mobileNumber;

        @FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.minicabit.android:id/login_password_panel']//android.widget.FrameLayout[1]")
        private WebElement password;

        @FindBy(xpath = "//android.widget.LinearLayout[@resource-id='com.minicabit.android:id/login_password_panel']//android.widget.FrameLayout[2]")
        private WebElement confirmPassword;

        @FindBy(id = "register_register_btn")
        private WebElement registerBtn;

        public CreateAccount() {
            refreshElements(this);
        }

        public CreateAccount fillYourDetails(YourDetails yourDetails) {
            logger.info("------------------Filling your details------------------");
            waitForElementToBeClickable(firstName);
            logger.info(String.format("Title -- %s",yourDetails.getTitle().name()));
            selectTitle(yourDetails.getTitle());
            logger.info(String.format("Firstname -- %s",yourDetails.getFirstName()));
            enterValue(firstName,yourDetails.getFirstName());
            logger.info(String.format("Lastname -- %s",yourDetails.getLastName()));
            enterValue(lastName,yourDetails.getLastName());
            return this;
        }

        public CreateAccount fillContactInformation(ContactInformation contactInformation) {
            String formattedEmail = String.format(contactInformation.getEmail(),getRandomEmailValue());
            logger.info("----------------Filling Contact Information---------------------");
            waitForElementToBeClickable(email);
            logger.info(String.format("Email -- %s",formattedEmail));
            enterValue(email,formattedEmail);
            logger.info(String.format(String.format("Mobile Number -- %s",contactInformation.getMobileNumber())));
            enterValue(mobileNumber,contactInformation.getMobileNumber());
            logger.info(String.format("Password -- %s",contactInformation.getPassword()));
            enterValue(password,contactInformation.getPassword());
            logger.info(String.format("Confirm Password -- %s",contactInformation.getConfirmPassword()));
            confirmPassword.click();
            enterValue(confirmPassword,contactInformation.getConfirmPassword());
            return this;
        }

        public CreateAccount createAccount() {
            waitForElementToBeClickable(registerBtn);
            registerBtn.click();
            logger.info("-----------------Creating account------------------------");
            return this;
        }

        public void selectTitle(Title titletype) {
            waitForElementToBeClickable(title);
            title.click();
            selectValueFromDropDown(titletype.name());
        }

        public void enterFirstName(String firstNameValue) {
            logger.info(String.format("Firstname -- %s",firstNameValue));
            enterValue(firstName,firstNameValue);
        }

        public void enterLastName(String lastNameValue) {
            logger.info(String.format("Lastname -- %s",lastNameValue));
            enterValue(lastName,lastNameValue);
        }

        public void enterEmail(String emailValue) {
            String formattedEmail = String.format(emailValue,getRandomEmailValue());
            waitForElementToBeClickable(email);
            logger.info(String.format("Email -- %s",formattedEmail));
            enterValue(email,formattedEmail);
        }

        public void enterMobileNumber(String mobileNumberValue) {
            logger.info(String.format(String.format("Mobile Number -- %s",mobileNumberValue)));
            enterValue(mobileNumber,mobileNumberValue);
        }

        public void enterPassword(String passwordValue) {
            logger.info(String.format("Password -- %s",passwordValue));
            enterValue(password,passwordValue);
        }

        public void enterConfirmPassword(String confirmPasswordValue) {
            logger.info(String.format("Password -- %s",confirmPasswordValue));
            enterValue(confirmPassword,confirmPasswordValue);
        }

    }

    private class Verification {

        private MandatoryFields mandatoryField;
        private WarningPopups warningPopup;

        public Verification(MandatoryFields mandatoryField) {
            this.mandatoryField = mandatoryField;
            this.warningPopup = pageStore.get(WarningPopups.class);
        }

        public Verification() {
            this.warningPopup = pageStore.get(WarningPopups.class);
        }

        public Verification verifyIfWarningIsPresent() {
            switch (mandatoryField) {
                case TITLE:
                    assertEquals(String.format("Title warning %s is not displayed",
                            MISSING_TITLE),warningPopup.isTitleWarning(),true);
                    break;
                case FIRST_NAME:
                    assertEquals(String.format("First name warning %s is not displayed",
                            MISSING_FIRST_NAME),warningPopup.isFirstNameWarning(),true);
                    break;
                case LAST_NAME:
                    assertEquals(String.format("Last name warning %s is not displayed",
                            MISSING_LAST_NAME),warningPopup.isLastNameWarning(),true);
                    break;
                case EMAIL:
                    assertEquals(String.format("Email warning %s is not displayed",
                            MISSING_EMAIL),warningPopup.isEmailWarning(),true);
                    break;
                case MOBILE_NUMBER:
                    assertEquals(String.format("Mobile number warning %s is not displayed",
                            MISSING_MOBILE_NUMBER),warningPopup.isMobileNumberWarning(),true);
                    break;
                case PASSWORD:
                    assertEquals(String.format("Password warning %s is not displayed",
                            MISSING_PASSWORD),warningPopup.isPasswordWarning(),true);
                    break;
                case CONFIRM_PASSWORD:
                    assertEquals(String.format("Confirm password warning %s is not displayed",
                            MISSING_CONFIRM_PASSWORD),warningPopup.isConfirmPasswordWarning(),true);
                    break;
                default:verifyAllWarnings();
                    break;
            }
            return this;
        }

        public Verification verifyAllWarnings() {
            assertEquals(String.format("Title warning %s is not displayed",
                    MISSING_TITLE),warningPopup.isTitleWarning(),true);
            assertEquals(String.format("First name warning %s is not displayed",
                    MISSING_FIRST_NAME),warningPopup.isFirstNameWarning(),true);
            assertEquals(String.format("Last name warning %s is not displayed",
                    MISSING_LAST_NAME),warningPopup.isLastNameWarning(),true);
            assertEquals(String.format("Email warning %s is not displayed",
                    MISSING_EMAIL),warningPopup.isEmailWarning(),true);
            assertEquals(String.format("Mobile number warning %s is not displayed",
                    MISSING_MOBILE_NUMBER),warningPopup.isMobileNumberWarning(),true);
            assertEquals(String.format("Password warning %s is not displayed",
                    MISSING_PASSWORD),warningPopup.isPasswordWarning(),true);
            assertEquals(String.format("Confirm password warning %s is not displayed",
                    MISSING_CONFIRM_PASSWORD),warningPopup.isConfirmPasswordWarning(),true);
            return this;
        }
    }

}
