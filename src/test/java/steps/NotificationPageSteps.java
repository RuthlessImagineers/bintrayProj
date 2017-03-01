package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import pages.NotificationPage;

/**
 * Created by nishant on 05/07/16.
 */
public class NotificationPageSteps {

    @And("^User verifies the Go-Jek notification \"([^\"]*)\"$")
    public void userVerifiesGoJekTheNotification(String notificationText) throws Throwable {
        new NotificationPage().checkForGoJekNotification(notificationText);
    }


}
