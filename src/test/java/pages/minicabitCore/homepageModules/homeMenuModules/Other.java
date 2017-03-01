package pages.minicabitCore.homepageModules.homeMenuModules;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.minicabitCore.homepageModules.HomeMenu;

/**
 * Created by krishnanand on 18/08/16.
 */
public class Other extends HomeMenu {

    @FindBy(id = "about_us_drawer_btn")
    private WebElement aboutUs;

    @FindBy(id = "how_it_works_drawer_btn")
    private WebElement howItWorks;

    @FindBy(id = "faq_drawer_btn")
    private WebElement faqs;

    @FindBy(id = "terms_drawer_btn")
    private WebElement termsAndConditions;

    @FindBy(id = "rate_drawer_btn")
    private WebElement rateThisApp;

    @FindBy(id = "version_drawer_btn")
    private WebElement version;


    public Other() throws Exception {

        //TODO:Will see when need be
    }
}
