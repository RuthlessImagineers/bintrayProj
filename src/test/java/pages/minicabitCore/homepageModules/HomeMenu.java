package pages.minicabitCore.homepageModules;

import enums.HomeMenuOptions;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.minicabitCore.Homepage;
import pages.minicabitCore.MyBookingspage;
import pages.minicabitCore.PassengerDetails;
import utils.DriverFactory;

public class HomeMenu extends Homepage {

    @FindBy(id = "login_drawer_btn")
    private WebElement loginOrRegisterUser;

    @FindBy(id = "book_a_trip_drawer_btn")
    private WebElement bookATrip;

    @FindBy(id = "tell_a_friend_drawer_btn")
    private WebElement tellAFriend;

    @FindBy(id = "contact_us_drawer_btn")
    private WebElement contactUs;

    @FindBy(id = "other_drawer_btn")
    private WebElement other;

    @FindBy(id="my_account_drawer_btn")
    private WebElement myAccount;

    private AppiumDriver driver;
    public HomeMenu() throws Exception {
        this.driver = DriverFactory.getDriver();
    }

    public  <T> T chooseHomeMenuOption(HomeMenuOptions homeMenuOption) {
        T pageToReturn = null;
        switch (homeMenuOption) {
            case LOGIN:
                pageToReturn = (T)login();
                break;
            case REGISTER:
                pageToReturn = (T)login();
                break;
            case BOOK_A_TRIP:
                //TODO: Will See when its needed
                break;
            case CONTACT_US:
                //TODO: Will See when its needed
                break;
            case TELL_A_FRIEND:
                //TODO: Will See when its needed
                break;
            case OTHER:
                //TODO: Will See when its needed
                break;
        }
        return pageToReturn;
    }

    public  <T> T chooseHomeMenuOptionWithMyAccountSubMenu(HomeMenuOptions.MyAccount subMenuOption) {
        T pageToReturn = (T) openMyAccountAndSelect(subMenuOption);
        return pageToReturn;
    }

    public PassengerDetails login() {
        waitForElementToBeClickable(loginOrRegisterUser);
        loginOrRegisterUser.click();
        return pageStore.get(PassengerDetails.class);
    }

    public <T> T openMyAccountAndSelect(HomeMenuOptions.MyAccount myAccountSubMenuOption) {
        logger.info("Selecting my account from home menu options");
        waitForElementToBeClickable(myAccount);
        myAccount.click();
        T page  =  (T) this.new MyAccount().chooseMyAccountSubMenu(myAccountSubMenuOption);
        return page;
    }

    private class MyAccount {

        @FindBy(id = "my_bookings_drawer_btn")
        private WebElement myBookings;

        @FindBy(id = "passenger_drawer_btn")
        private WebElement passengerInfo;

        @FindBy(id = "billing_drawer_btn")
        private WebElement billing;

        @FindBy(id = "account_drawer_btn")
        private WebElement account;


        public MyAccount() {
            refreshElements(this);
        }

        public <T> T chooseMyAccountSubMenu(HomeMenuOptions.MyAccount myAccountSubMenuOption) {
            T pageToReturn = null;
            switch (myAccountSubMenuOption) {
                case MY_BOOKINGS:
                    pageToReturn = (T) navigateToMyBookingsPage();
                    break;
                case PASSENGER_INFO:
                    break;
                case BILLING:
                    break;
                case ACCOUNT:
                    break;
            }
            return pageToReturn;
        }


        private MyBookingspage navigateToMyBookingsPage() {
            waitForElementToBeClickable(myBookings);
            myBookings.click();
            return pageStore.get(MyBookingspage.class);
        }

    }




}
