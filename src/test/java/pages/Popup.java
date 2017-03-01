package pages;

import exceptions.UnknownServerException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;
import utils.locatorUtils.Bys;
import utils.DriverFactory;

import static utils.Constants.PopupMessages.*;
import static org.junit.Assert.*;

public class Popup extends BasePage {


    @FindBy(id = "emailSubmitBtn")
    private WebElement noCabsAvailableReferenceElement;

    @FindBy(id = "button1")
    private WebElement btn_ok;

    @FindBy(id = "button2")
    private WebElement btn_no;

    @FindBy(id = "text_view")
    private WebElement hiddenCabOperatorDetailsReferenceElement;

    @FindBy(id = "text_view")
    private WebElement popupText;

    @FindBy(id = "text1")
    private WebElement addressToSelect;

    @FindBy(id = "edit_text")
    private WebElement editText;

    private AppiumDriver driver;
    private WebDriverWait popupWait;
    public Popup() throws Exception {
        this.driver = DriverFactory.getDriver();
        popupWait = new WebDriverWait(driver,15);
    }


    public <T> void handleNoCabsAvaliablePopup(T parentPage) {
        try {
            handleErrorPopup();
            if (noCabsAvailableReferenceElement == null)
                refreshElements(this);
            waitForElementToBeClickable(btn_ok);
            btn_ok.click();
        }catch (Exception e) {
            //Just to understand that popup was not expected here
        }
        refreshElements(parentPage);
    }

    public <T> void handleHiddenCabOperatorDetailsPopup(T parentPage) {
        try{
            String message = getPopupMessage();
            if(message.equals(HIDDEN_CAB_OPERATORS)) {
                if (hiddenCabOperatorDetailsReferenceElement == null)
                    refreshElements(this);

                waitForElementToBeClickable(btn_ok);
                btn_ok.click();
            } else {
                handleErrorPopup();
            }
        } catch (Exception e) {
            //Just to understand that popup was not expected here
        }
        refreshElements(parentPage);
    }

    public <T> void handleSimilarLuggageSelectionPopup(T parentPage) {
        try {
            handleErrorPopup();
            String message = getPopupMessage();
            logger.info(String.format("Found a popup with message \n %s",message));
            if(message.equals(SIMILAR_LUGGAGE_SELECTION)) {
                btn_ok.click();
            }
        } catch (Exception e) {
            //Just to understand that popup was not expected here
        }
        refreshElements(parentPage);
    }

    public <T> void handleVerifyEmailAddressPopup(T parentPage) {
        try{
            handleErrorPopup();
            String message = getPopupMessage();
            assertEquals(String.format("Expected email address popup with message " +
                    "%s is not present but a popup with message %s is displayed",VERIFY_EMAIL,message),VERIFY_EMAIL,message);
            if(btn_ok==null)
                refreshElements(this);
            waitForElementToBeClickable(btn_ok);
            btn_ok.click();
        } catch (Exception e) {
            //Just to understand that popup was not expected here
        }
        refreshElements(parentPage);
    }

    public <T> void handleNoCashPopup(T parentPage) {
        try {
            handleErrorPopup();
            if(btn_ok==null) {
                refreshElements(this);
            }
            String popupMessage = getPopupMessage();
            if(popupMessage.equals(NO_CASH))
                btn_ok.click();
        } catch (Exception e) {

        }
        refreshElements(parentPage);
    }

    public <T> void handleRegisterEmailExistsPopup(T parentpage) {
            try {
                handleErrorPopup();
                String message = getPopupMessage();
                assertEquals(String.format("Expected message is %s while the message displayed is %s",
                        REGISTER_EMAIL_EXISTS,message),REGISTER_EMAIL_EXISTS,message);
                btn_ok.click();
            } catch (Exception e) {

            }
    }


    public <T> void handleBuldingNumberPopup(String buildingNumber, T parentPage) {
        try {
            handleErrorPopup();
            popupWait.until(ExpectedConditions.textToBePresentInElement(popupText,VIA_BUILDING_NUMBER));
            sendKeys(editText,buildingNumber);
            btn_ok.click();
        }catch (Exception e) {

        }
        refreshElements(parentPage);
    }

    public <T> void handleClearTripDetailsPopup(boolean clearDetails, T parentPage) {
        handleErrorPopup();
        verifyIfClearTripDetailsPopupIsPresent();
        if(clearDetails)
            btn_ok.click();
        else
            btn_no.click();
        refreshElements(parentPage);
    }

    public <T> void handleMultiButtonPopup(String popupMessage, boolean yesOrNo,T parentPage) {
        handleErrorPopup();
        verifyIfPopupIsDesiredOne(popupMessage);
        if(yesOrNo)
            btn_ok.click();
        else
            btn_no.click();
        refreshElements(parentPage);
    }

    public <T> boolean handleSingleButtonPopup(String popupMessage, T parentPage) {
        boolean desiredPopup = verifyIfPopupIsDesiredOne(popupMessage);
        if(desiredPopup) {
            btn_ok.click();
            refreshElements(parentPage);
        } else {
            handleErrorPopup();
        }
        return desiredPopup;
    }

    public boolean verifyIfPopupIsDesiredOne(String message) {
        String popupMessage = getPopupMessage();
        assertEquals(String.format("Expected popup message %s does not contain %s",
                popupMessage,message),popupMessage.contains(message), true);
        return true;
    }

    public void verifyIfClearTripDetailsPopupIsPresent() {
        String popupMessage = getPopupMessage();
        assertEquals(String.format("Expected a popup with message %s but found %s",
                WARNING_CLEAR_TRIP_DETAILS,popupMessage),popupMessage,WARNING_CLEAR_TRIP_DETAILS);
    }


    public boolean verifyIfAErrorPopupIsDisplayed(String errorMessage) {
        try {
            String popupMessage = getPopupMessage();
            if(popupMessage.equals(errorMessage)) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }


    public String getPopupMessage() {
        if(popupText ==null)
            refreshElements(this);
        return popupText.getText();
    }

    public void selectExpiryDate(String month, String year) {
        System.out.println(month + " "+ year);
        MobileElement currentMonthElement = (MobileElement) driver.findElement(Bys.CURRENT_EXPIRY_MONTH);
        MobileElement currentYearElement = (MobileElement)driver.findElement(Bys.CURRENT_EXPIRY_YEAR);
        MobileElement nextMonthBtn = (MobileElement) driver.findElement(Bys.EXPIRY_MONTH_BTN);
        MobileElement nextYearBtn = (MobileElement) driver.findElement(Bys.EXPIRY_YEAR_BTN);
        boolean validMonthSelected = false;
        boolean validYearSelected = false;
        try {
            while (!validMonthSelected) {
                if (currentMonthElement.getText().equalsIgnoreCase(month)) {
                    validMonthSelected = true;
                } else {
                    nextMonthBtn.tap(1, 0);
                    waitForElementToBeClickable(nextMonthBtn);
                    nextMonthBtn = (MobileElement) driver.findElement(Bys.EXPIRY_MONTH_BTN_BOTTOM);
                    currentMonthElement = (MobileElement) driver.findElement(Bys.CURRENT_EXPIRY_MONTH);
                }
            }
        } catch (Exception e) {

        }

        try {
            while (!validYearSelected) {
                if(currentYearElement.getText().equalsIgnoreCase(year)) {
                    validYearSelected = true;
                } else {
                    nextYearBtn.tap(1, 0);
                    waitForElementToBeClickable(nextYearBtn);
                    nextYearBtn = (MobileElement) driver.findElement(Bys.EXPIRY_YEAR_BTN_BOTTOM);
                    currentYearElement = (MobileElement)driver.findElement(Bys.CURRENT_EXPIRY_YEAR);
                }
            }
        } catch (Exception e) {

        }

        btn_ok.click();
    }


    public void selectAddress() {
        if(addressToSelect==null)
            refreshElements(this);
        waitForElementToBeClickable(addressToSelect);
        logger.info(String.format("Selected address \n %s",addressToSelect.getText()));
        addressToSelect.click();
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
    }

    public void waitForElementToBeClickable(WebElement element) {
            popupWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void handleErrorPopup() {
        boolean errorPopup = verifyIfAErrorPopupIsDisplayed(Constants.PopupMessages.ERROR_SERVER);
        if(errorPopup) {
            try {
                throw new UnknownServerException();
            } catch (UnknownServerException e) {
                e.printStackTrace();
            }
        }
    }
}
