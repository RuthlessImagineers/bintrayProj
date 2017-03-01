package pages.minicabitCore.homepageModules;

import entity.From;
import entity.To;
import entity.journeyDetails.Via;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.Popup;
import pages.minicabitCore.Homepage;
import utils.locatorUtils.Bys;
import utils.DriverFactory;
import enums.LocationType;

import java.util.List;

import static utils.Constants.PopupMessages.*;
import static utils.Constants.CommonData.*;
import static utils.Constants.FavouriteAs.*;
import static org.junit.Assert.*;
/**
 * Created by krishnanand on 25/07/16.
 */
public class Location extends BasePage {

    private AppiumDriver driver;

    @FindBy(id = "actionBarTextView")
    private WebElement locationType;

    @FindBy(id= "from_to_address_tv")
    private WebElement displayAddressBar;

    @FindBy(id = "from_to_recents_address_tv")
    private WebElement addressBar;

    @FindBy(id = "from_to_recents_rv")
    private WebElement searchResultsLayout;

    @FindBy(id = "resentAddressTv")
    private List<WebElement> searchResults;

    @FindBy(id = "android:id/progress")
    private WebElement progressBar;

    @FindBy(id = "action_from_to_done")
    private WebElement selectAddress;



    public Location() throws Exception {
        this.driver = DriverFactory.getDriver();
    }

    public <E> Location enterLocation(E location) throws Exception {
        String locationToSearch = "";
        waitForElementToBeClickable(displayAddressBar);
        displayAddressBar.click();
        waitForElementToBeClickable(addressBar);
        if(location instanceof From) {
            locationToSearch = ((From) location).getPickupLocation();
            addressBar.click();
            driver.getKeyboard().sendKeys(locationToSearch);
        } else if(location instanceof To) {
            locationToSearch = ((To) location).getDropOffLocation();
            addressBar.click();
            driver.getKeyboard().sendKeys(locationToSearch);
        } else if(location instanceof Via) {
            locationToSearch = ((Via) location).getLocation();
            sendKeys(addressBar,locationToSearch);
        }

        selectValidLocationFromSearchResults(locationToSearch);

        return new Location();
    }


    private void selectValidLocationFromSearchResults(String location) {
        waitForElementToBeVisible(searchResultsLayout);
        for(WebElement searchResult: searchResults) {
            String locationSuggested = searchResult.getText();
            if(locationSuggested.contains(location)) {
                searchResult.click();
                break;
            }
        }

    }

    public Homepage selectLocation() throws Exception {
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
        if(selectAddress==null)
            refreshElements(this);
        selectAddress.click();
        return new Homepage();
    }

    public Vias selectLocationAndEnterBuildingAddress(String buildingNumber) throws Exception {
        waitForElementToBeInvisible(Bys.PROGRESS_SPINNER);
        if(selectAddress==null)
            refreshElements(this);
        selectAddress.click();
        pageStore.get(Popup.class).handleBuldingNumberPopup(buildingNumber,Vias.class);
        return new Vias();
    }

    public boolean isValidLocation(LocationType location) {
        waitForElementToBeVisible(locationType);
        return locationType.getText().toUpperCase().equals(location.name());
    }

    public Location navToFavourites() {
        List<WebElement> addresses = driver.findElements(By.id("from_to_address_tv"));
        if(addresses.size()>0) {
            logger.info("Navigating to favourites");
            waitForElementToBeClickable(displayAddressBar);
            displayAddressBar.click();
        }
        return this;
    }

    public Location removeAnyExitingFavourites() {
        Favourites favourites = this.new Favourites();
        favourites.removeExistingFavourites();
        return this;
    }

    public Location addFavourites() {
        navToFavourites();
        Favourites favourites = this.new Favourites();
        favourites.addHomeFavourite();
        navToFavourites();
        favourites.addWorkFavourite();
        return this;
    }

    public Location verifyIfFavouritesAreAdded() {
        logger.info("Verifying favourite count..");
        int favourites = this.new Favourites().getFavouritesCount();
        assertEquals(String.format("Expected favourites is " +
                "%s but found only %s favourites",2,favourites),favourites,2);
        return this;
    }

    private class Favourites {
        @FindBy(id ="recentAddressHeartIv")
        private List<WebElement> recentAddressHeart;

        @FindBy(id = "resentAddressTv")
        private List<WebElement> recentAddressNames;

        @FindBy(id = "set_favourite_dialog_home_btn")
        private WebElement favourite_home;

        @FindBy(id = "set_favourite_dialog_work_btn")
        private WebElement favourite_work;

        @FindBy(id = "set_favourite_dialog_cancel_btn")
        private WebElement favourite_cancel;

        @FindBy(id = "set_favourite_dialog_fav_btn")
        private WebElement favourite_favourite;

        public Favourites() {
            refreshElements(this);
        }

        public Favourites removeExistingFavourites() {
            while (recentAddressHeart.size()!=0) {
                recentAddressHeart.get(0).click();
                pageStore.get(Popup.class).handleMultiButtonPopup(WARNING_FAVOURITE_MESSAGE,true,this);
                waitForElementToBeInvisible(Bys.LOADER);
                refreshElements(this);
            }
            return this;
        }

        public void addHomeFavourite() {
            addressBar.click();
            addressBar.sendKeys(HOME_FAVOURITE);
            selectValidLocationFromSearchResults(HOME_FAVOURITE);
            addAddressToFavouritesAs(HOME_FAVOURITE,HOME);
        }

        public void addWorkFavourite() {
            addressBar.click();
            addressBar.sendKeys(WORK_FAVOURITE);
            selectValidLocationFromSearchResults(WORK_FAVOURITE);
            addAddressToFavouritesAs(WORK_FAVOURITE,WORK);
        }

        private void addAddressToFavouritesAs(String address,String favouriteAs) {
            navToFavourites();
            refreshElements(this);
            recentAddressHeart.get(getLocationOfThisAddressInCurrentAddress(address)).click();
            refreshElements(this);
            switch (favouriteAs) {
                case HOME:
                      waitForElementToBeClickable(favourite_home);
                      favourite_home.click();
                    break;
                case WORK:
                    waitForElementToBeClickable(favourite_work);
                    favourite_work.click();
                    break;
            }
            waitForElementToBeInvisible(Bys.LOADER);
        }

        private int getLocationOfThisAddressInCurrentAddress(String thisAddress) {
            for(int i=0; i<recentAddressNames.size(); i++) {
                String text = recentAddressNames.get(i).getText();
                if(text.contains(thisAddress))
                    return i;
            }
            return 0;
        }

        public int getFavouritesCount() {
            navToFavourites();
            refreshElements(this);
            return recentAddressHeart.size();
        }

    }
}
