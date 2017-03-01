package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import pages.DeviceUtil;

public class DeviceUtilPageSteps extends BaseSteps {

    @Then("^On Device I turn network \"([^\"]*)\"$")
    public void onDeviceITurnNetwork(String On_Off) throws Throwable {
        new DeviceUtil().toggleNetworkOnAndroid(On_Off);
    }

    @Then("^On Device I navigate back$")
    public void onDeviceINavigateBack() throws Throwable {
        new DeviceUtil().naivgateBackOnAndroid();
    }

    @Then("^On Device I take screenshot \"([^\"]*)\"$")
    public void onDeviceITakeScreenshot(String imageName) throws Throwable {
        new DeviceUtil().takeAScreenShotOfApp(imageName);
    }

    @And("^On Device I turn location services \"([^\"]*)\"$")
    public void onDeviceITurnLocationServices(String on_off) throws Throwable {
        new DeviceUtil().toggleLocation(on_off);
    }

}
