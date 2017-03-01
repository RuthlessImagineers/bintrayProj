package pages.minicabitCore.homepageModules;

import entity.TripDetails;
import entity.journeyDetails.Via;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import pages.minicabitCore.Homepage;

import java.util.List;

public class Vias extends Homepage {

    @FindBy(id = "viaAddress")
    private List<WebElement> viaAddress;

    @FindBy(id = "removeViaView")
    private List<WebElement> removeVia;

    @FindBy(id = "addViaBtnIv")
    private WebElement viaButton;


    private List<Via> vias;
    private Location location;

    public Vias() throws Exception {
        this.vias = pageStore.get(TripDetails.class).getVias();
        this.location = pageStore.get(Location.class);
    }

    public boolean enterVias() throws Exception {
        boolean enteredVias = false;
        if(vias!=null) {
            homepageLogger.info("Entering vias for trip");
            for (Via via : vias) {
                waitForElementToBeClickable(viaButton);
                viaButton.click();
                waitForPresenceOfAllElements(By.id("viaAddress"));
                int viaAddressAvailable = viaAddress.size();
                viaAddress.get(viaAddressAvailable - 1).click();
                logger.info(String.format("Entering via location as %s and building number as %s", via.getLocation(), via.getBuildingNumber()));
                if (via.getBuildingNumber() == null)
                    location.enterLocation(via).selectLocation();
                else
                    location.enterLocation(via).selectLocationAndEnterBuildingAddress(via.getBuildingNumber());
                enteredVias = true;
            }
        }
        return enteredVias;
    }
}
