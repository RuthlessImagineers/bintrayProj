package utils;

import enums.Drivers;
import exceptions.MissingPropertyException;
import org.apache.xerces.util.SymbolTable;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilitiesFactory {

    private static DesiredCapabilities capabilities = new DesiredCapabilities();

    private CapabilitiesFactory() {
        //Preventing this from initialization
    }

    public static DesiredCapabilities getCapabilities() throws MissingPropertyException{
        switch (getDriverType()) {
            case LOCAL:
                capabilities = new LocalDriver().getDesiredCapabilities();
                break;
            case SAUCE:
                capabilities = new SauceDriver().getDesiredCapabilities();
                break;
        }
        return capabilities;
    }

    private static Drivers getDriverType() throws MissingPropertyException {
        String driver = System.getProperty("driver");
        if(driver==null)
            throw new MissingPropertyException("Where do you want to run your tests local or sauce?. " +
                    "You can specify this requirement as vm argument -Ddriver='local/sauce'");
        return Drivers.valueOf(driver.toUpperCase());
    }

}
