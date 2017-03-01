package steps.minicabitCore;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.minicabitCore.homepageModules.Location;
import steps.BaseSteps;

public class LocationPageSteps extends BaseSteps {

    @And("^On Location page I should be able to remove any existing favourites$")
    public void onLocationPageIShouldBeAbleToRemoveAnyExistingFavourites() throws Throwable {
        pageStore.get(Location.class).navToFavourites().removeAnyExitingFavourites();
    }

    @When("^On Location page I add my home and work favourites$")
    public void onLocationPageIAddMyHomeAndWorkFavourites() throws Throwable {
        pageStore.get(Location.class).addFavourites();
    }

    @Then("^On Location page my favourite locations should be added$")
    public void onLocationPageMyFavouriteLocationsShouldBeAdded() throws Throwable {
        pageStore.get(Location.class).verifyIfFavouritesAreAdded();
    }
}
