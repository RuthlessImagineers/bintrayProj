package pages.minicabitCore.PaymentPageModules;

import entity.payment.Card;
import entity.payment.CardDetails;
import entity.payment.ExpiryDate;
import enums.CardType;
import enums.PaymentType;
import exceptions.OutOfScopeException;
import exceptions.UnsupportedCardTypeException;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;
import pages.Popup;
import utils.locatorUtils.Bys;
import utils.DriverFactory;

import static utils.Constants.Extenstions.*;
import static utils.Constants.FolderLocations.*;
import static utils.Constants.CardTypes.*;

import java.util.List;

public class PaymentPage extends BasePage {
    private AppiumDriver driver;

    @FindBy(id = "select_payment_pay_by_card_ll")
    private WebElement payByCard;

    @FindBy(id = "select_payment_pay_by_new_card_ll")
    private WebElement payByNewCard;

    @FindBy(id = "select_payment_pay_by_paypal_ll")
    private WebElement payByPayPal;

    @FindBy(id = "select_payment_pay_by_pingit_ll")
    private WebElement payByPingIt;

    @FindBy(id = "select_payment_pay_by_cash_ll")
    private WebElement payByCash;

    @FindBy(id = "select_payment_book_trip_btn")
    private WebElement saveBtn;

    public PaymentPage() throws Exception {
        driver = DriverFactory.getDriver();
    }

    public PaymentPage makePaymentWith(String paymentType,String cardDetails, String cardType) throws OutOfScopeException, UnsupportedCardTypeException {
        PaymentType payment = enumMapper.getPaymentType(paymentType);
        CardType card = enumMapper.getCardType(cardType);
        switch (payment) {
            case CARD:
                logger.info("Paying with a new card of type "+cardType);
                paymentWithCard(card,cardDetails);
                break;
            case EXISTINGCARD:
                logger.info("Paying with existing card");
                paymentWithExistingCard(card,cardDetails);
                break;
            case PAYPAL:
                logger.info("Paying with paypal");
                paymentWithPaypal(cardDetails);
                break;
            case PINGIT:
                logger.info("Paying with pingit");
                paymentWithPingIt();
                break;
            case CASH:
                logger.info("Paying with cash");
                paymentWithCash();
                break;
        }
        saveBtn.click();
        return this;
    }

    private void paymentWithCard(CardType cardType, String cardDetails) {
        CardDetails cardDetails1 = (CardDetails) dataMapper.mapDetails(CardDetails.class,CARDDATA+cardDetails+JSON);
        Card card = getCard(cardDetails1.getCard(),cardType);
        logger.info(String.format("User selected to pay with card %s",card.getName()));
        waitForElementToBeClickable(payByNewCard);
        payByNewCard.click();
        enterCardDetails(cardType,card, cardDetails1);
    }

    private void enterCardDetails(CardType cardType, Card card, CardDetails cardDetails) {
        logger.info("Entering new card details");
        this.new PayByCard(cardType,card,cardDetails)
                 //.selectCardType()
                 .enterCardNo()
                 .selectExpiryDate()
                 .enterCVV()
                 .enterCardOwnerDetails()
                 .selectAddress();
    }



    private Card getCard(List<Card> cardStack, CardType cardType) {
        Card card = null;
        switch (cardType) {
            case AMERICANEXPRESS:
                card = getCardFrom(cardStack,CardType.AMERICANEXPRESS);
                break;
            case MAESTRO:
                card = getCardFrom(cardStack,CardType.MAESTRO);
                break;
            case MASTERCARDCREDIT:
                card = getCardFrom(cardStack,CardType.MASTERCARDCREDIT);
                break;
            case MASTERCARDDEBIT:
                card = getCardFrom(cardStack,CardType.MASTERCARDDEBIT);
                break;
            case VISACREDIT:
                card = getCardFrom(cardStack,CardType.VISACREDIT);
                break;
            case VISADEBIT:
                card = getCardFrom(cardStack,CardType.VISADEBIT);
                break;
            case VISAELECTRON:
                card = getCardFrom(cardStack,CardType.VISAELECTRON);
                break;
            default:
                card = getCardFrom(cardStack,CardType.AMERICANEXPRESS);
                break;
        }
        return card;
    }

    private Card getCardFrom(List<Card> cardsStack, CardType cardType) {
        Card requiredCard = null;
        String requiredCardName = cardType.name().toLowerCase();
        for(Card card : cardsStack) {
            String cardName = card.getName().toLowerCase();
            if(cardName.equals(requiredCardName)) {
                requiredCard = card;
                break;
            }
        }
        return requiredCard;
    }

    private void paymentWithPaypal(String paypalDetails) {
        //TODO:
    }

    private void paymentWithPingIt() throws OutOfScopeException {
        throw new OutOfScopeException("PaymentPage with pingit, needs pingit application to be installed. For now we" +
                " are holding it as out of scope");
    }

    private void paymentWithCash() {
        waitForElementToBeClickable(payByCash);
        payByCash.click();
    }

    private void paymentWithExistingCard(CardType cardType, String cardDetails) {
        try {
            waitForElementToBeClickable(payByCard);
            payByCard.click();
        } catch (Exception e) {
            paymentWithCard(cardType,cardDetails);
        }

    }

    private class PayByCard {

        @FindBy(id = "payment_activity_card_type_spinner")
        private WebElement cardTypeEle;

        @FindBy(id = "payment_card_number_et")
        private WebElement cardNumber;

        @FindBy(id = "payment_expiry_date_et")
        private WebElement expiryDate;

        @FindBy(id = "payment_cvv_code_et")
        private WebElement cvvCode;

        @FindBy(id = "payment_first_name_et")
        private WebElement firstName;

        @FindBy(id = "payment_last_name_et")
        private WebElement lastName;

        @FindBy(id = "payment_postcode_et")
        private WebElement postCode;

        @FindBy(id = "card_search_address")
        private WebElement findAddress;

        @FindBy(id = "text1")
        private WebElement addressToSelect;


        private CardType cardType;
        private Card card;
        private CardDetails cardDetails;
        public PayByCard(CardType cardType, Card card, CardDetails cardDetails) {
            this.cardType = cardType;
            this.card = card;
            this.cardDetails = cardDetails;
            PageFactory.initElements(driver,this);
        }

        public PayByCard selectCardType() {
            waitForElementToBeClickable(cardTypeEle);
            cardTypeEle.click();
            switch (cardType) {
                case VISACREDIT:
                    selectCardType(VISA_CREDIT);
                    break;
                case VISADEBIT:
                    selectCardType(VISA_DEBIT);
                    break;
                case VISAELECTRON:
                    selectCardType(VISA_ELECTRON);
                    break;
                case MASTERCARDDEBIT:
                    selectCardType(MASTERCARD_DEBIT);
                    break;
                case MASTERCARDCREDIT:
                    selectCardType(MASTERCARD_CREDIT);
                    break;
                case MAESTRO:
                    selectCardType(MAESTRO);
                    break;
                case AMERICANEXPRESS:
                    selectCardType(AMERICAN_EXPRESS);
                    break;
            }
            return this;
        }

        private void selectCardType(String cardName) {
            logger.info(String.format("Selecting card type -- %s",cardName));
            List<WebElement> cardTypes = driver.findElements(By.id("text1"));
            for(WebElement cardType : cardTypes) {
                String text = cardType.getText();
                if(text.equals(cardName)) {
                    cardType.click();
                    break;
                }
            }
        }

        public PayByCard enterCardNo() {
            moveElementToThisElementPos(cardNumber,firstName);
            waitForElementToBeClickable(cardNumber);
            logger.info(String.format("Entering card number as -- %s",card.getCardNo()));
            sendKeys(cardNumber,card.getCardNo());
            return this;
        }

        public PayByCard selectExpiryDate() {
            waitForElementToBeClickable(expiryDate);
            ExpiryDate date = cardDetails.getExpiryDate();
            expiryDate.click();
            logger.info(String.format("Selecting expiry date as -- %s-%s",date.getMonth(),date.getYear()));
            pageStore.get(Popup.class).selectExpiryDate(date.getMonth(),date.getYear());
            driver.hideKeyboard();
            return this;
        }

        public PayByCard enterCVV() {
            waitForElementToBeClickable(cvvCode);
            cvvCode.click();
            logger.info(String.format("Entering CVV as -- %s",cardDetails.getCvv()));
            sendKeys(cvvCode,cardDetails.getCvv());
            return this;
        }

        public  PayByCard enterCardOwnerDetails() {
            waitForElementToBeVisible(firstName);
            logger.info("Entering card owner details");
            logger.info(String.format("FirstName -- %s",cardDetails.getFirstName()));
            sendKeys(firstName,cardDetails.getFirstName());
            moveElementToThisElementPos(firstName,lastName);
            waitForElementToBeVisible(lastName);
            logger.info(String.format("LastName -- %s",cardDetails.getLastName()));
            sendKeys(lastName,cardDetails.getLastName());
            return this;
        }

        public PayByCard selectAddress() {
            logger.info("Selecting user address");
            moveElementToThisElementPos(firstName,postCode);
            logger.info("Postcode --"+cardDetails.getPostcode());
            waitForElementToBeVisible(postCode);
            sendKeys(postCode,cardDetails.getPostcode());
            waitForPresenceOfElement(By.id("chat_button"));
            waitForElementToBeClickable(findAddress);
            findAddress.click();
            try {
                waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
            } catch (Exception e) {
                driver.hideKeyboard();
                findAddress.click();
            }
            pageStore.get(Popup.class).selectAddress();
            return this;
        }


    }
}
