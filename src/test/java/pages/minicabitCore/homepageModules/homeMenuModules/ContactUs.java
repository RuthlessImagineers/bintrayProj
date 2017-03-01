package pages.minicabitCore.homepageModules.homeMenuModules;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.minicabitCore.homepageModules.HomeMenu;

/**
 * Created by krishnanand on 18/08/16.
 */
public class ContactUs extends HomeMenu {

    @FindBy(id = "contact_us_live_chat_drawer_btn")
    private WebElement liveChat;

    @FindBy(id = "contact_us_email_us_drawer_btn")
    private WebElement emailUs;

    public ContactUs() throws Exception {

        //TODO:Will see when need be
    }
}
