package pages.minicabitCore.homepageModules;


import builders.CabBuilder;
import entity.TripDetails;
import entity.searchResults.Cab;
import enums.PaymentType;
import exceptions.NoCabFoundException;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.minicabitCore.Homepage;

import static utils.Constants.CommonData.*;
import static utils.Constants.Widgets.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CabResultsModule extends Homepage {

    private AppiumDriver driver;
    private Cab cab;
    private Set<String> cabProvidersSet = new TreeSet<>();
    private boolean cabProviderAdded;
    private int cabLocation;

    @FindBy(id = "main_screen_providers_list_view")
    private List<WebElement> cabsList;

    @FindBy(id = "outgoingLayout")
    private WebElement outgoingCabs;

    @FindBy(id = "returnLayout")
    private WebElement returnCabs;

    private WebElement providerTitle;
    private WebElement providerPrice;
    private WebElement providerBase;
    private WebElement providerEmission;
    private WebElement providerPaymentType;

    @FindBy(id = "textView6")
    private WebElement providerSelect;

    private List<WebElement> cabProviders;

    private WebElement cabProviderToSelect;
    private static Logger cabResultsModuleLogger;
    private boolean cabDetailsSet;

    public CabResultsModule() throws Exception {
        driver = super.driver;
        PageFactory.initElements(driver,this);
        cabResultsModuleLogger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Selects a default cab, mostly first one.
     * This can be used if you are fine with any cab.
     * @return
     */
    public void selectACab() {
        cabProviderToSelect.click();
    }

    public void buildCabDetails(PaymentType paymentType) {
        if(!cabDetailsSet) {
            setupCabDetails(cabsList.get(0));
        }
        if(providerSelect == null)
            refreshElements(this);
        try{
            cab = pageStore.get(CabBuilder.class)
                    .withCabProviderTitle(providerTitle)
                    .withCabProviderPrice(providerPrice,paymentType)
                    .withCabProviderBase(providerBase)
                    .withCabProviderEmission(providerEmission)
                    .withCabProviderPaymentType(providerPaymentType)
                    .build();
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }


    public  TripDetails selectACabForSingleJourney(TripDetails tripDetails, PaymentType paymentType) throws NoCabFoundException {
        selectACab(paymentType);
        tripDetails.setCab(cab);
        return tripDetails;
    }

    /**
     * Use <@code>selectACab(PaymentType paymentType)</@code> instead
     * @param tripDetails
     * @param paymentType
     * @throws NoCabFoundException
     */
    @Deprecated
    public void selectACab(TripDetails tripDetails, PaymentType paymentType) throws NoCabFoundException {
        boolean cabFound = false;
        WebElement lastCabProvider = null;
        WebElement firstCabProvider = null;
        waitForPresenceOfAllElements(CabProviderBys.CAB_PROVIDERS);
        while (!cabFound) {
            cabProviders = driver.findElements(CabProviderBys.CAB_PROVIDERS);
            int totalCabProvidersVisible = cabProviders.size();
            firstCabProvider = cabProviders.get(0);
            lastCabProvider = cabProviders.get(totalCabProvidersVisible-1);
            //for (WebElement cabProvider : cabProviders) {
            for(WebElement cabProvider : cabProviders) {
                providerPaymentType = updateElementFrom(CabProviderIds.PROVIDER_PAYMENT_TYPE);
                providerTitle = updateElementFrom(CabProviderIds.PROVIDER_TITLE);
                logger.info("Provider title "+providerTitle.getText());
                addACabProviderToList(providerTitle.getText());
                if (providerPaymentType == null) {
                    cabProviderToSelect = cabProvider;
                    cabFound = true;
                    break;
                } else if (providerPaymentType.getText().contains(CARD_ONLY) && (paymentType.equals(PaymentType.CARD) || paymentType.equals(PaymentType.EXISTINGCARD))) {
                    cabProviderToSelect = cabProvider;
                    cabFound = true;
                    break;
                } else if (providerPaymentType.getText().contains(CASH_ONLY) && paymentType.equals(PaymentType.CASH)) {
                    cabProviderToSelect = cabProvider;
                    cabFound = true;
                    break;
                }
                moveElementToThisElementPos(firstCabProvider,lastCabProvider);
            }
            if(lastCabProvider==null) {
                throw new NoCabFoundException();
            }
            if(!cabFound) {
                refreshElements(this);
            }

            if(!cabProviderAdded) {
                throw new NoCabFoundException(String.format("Could not find a cab with payment mode %s hence quitting",paymentType));
            }

        }

        setupCabDetails(lastCabProvider);
        buildCabDetails(paymentType);
        selectACab();
    }

    /**
     * Trying with old school logic of finding an element with xpaths
     * @param paymentType
     * @throws NoCabFoundException
     */

    public void selectACab(PaymentType paymentType) throws NoCabFoundException {
        boolean cabFound = false;
        WebElement lastCabProvider = null;
        WebElement firstCabProvider = null;
        waitForPresenceOfAllElements(CabProviderBys.CAB_PROVIDERS);
        while (!cabFound) {
            cabProviders = driver.findElements(CabProviderBys.CAB_PROVIDERS);
            int totalCabProvidersVisible = cabProviders.size();
            firstCabProvider = cabProviders.get(0);
            lastCabProvider = cabProviders.get(totalCabProvidersVisible-1);
            //for (WebElement cabProvider : cabProviders) {
            for(int i=1; i<=cabProviders.size(); i++) {
                cabLocation = i;
                WebElement cabProvider = getCabFromList(cabLocation);
                providerPaymentType = updateElementFrom(CabProviderIds.PROVIDER_PAYMENT_TYPE);
                providerTitle = updateElementFrom(CabProviderIds.PROVIDER_TITLE);
                logger.info("Provider title "+providerTitle.getText());
                addACabProviderToList(providerTitle.getText());
                if (providerPaymentType == null) {
                    cabProviderToSelect = cabProvider;
                    cabFound = true;
                    break;
                } else if (providerPaymentType.getText().contains(CARD_ONLY) && (paymentType.equals(PaymentType.CARD) || paymentType.equals(PaymentType.EXISTINGCARD))) {
                    cabProviderToSelect = cabProvider;
                    cabFound = true;
                    break;
                } else if (providerPaymentType.getText().contains(CASH_ONLY) && paymentType.equals(PaymentType.CASH)) {
                    cabProviderToSelect = cabProvider;
                    cabFound = true;
                    break;
                }
                moveElementToThisElementPos(firstCabProvider,lastCabProvider);
            }
            if(lastCabProvider==null) {
                throw new NoCabFoundException();
            }
            if(!cabFound) {
                refreshElements(this);
            }

            if(!cabProviderAdded) {
                throw new NoCabFoundException(String.format("Could not find a cab with payment mode %s hence quitting",paymentType));
            }

        }

        setupCabDetails(lastCabProvider);
        buildCabDetails(paymentType);
        selectACab();
    }

    public TripDetails selectACabForReturnJourney(TripDetails tripDetails, PaymentType paymentType) throws NoCabFoundException {
        if(isReturnAvailable()) {
            refreshElements(this);
            selectACabForSingleJourney(tripDetails,paymentType);
            cabProvidersSet.clear();
            selectACab(paymentType);
            tripDetails.setReturnCab(cab);
        } else {
            selectACabForSingleJourney(tripDetails,paymentType);
        }

        return tripDetails;
    }

    private WebElement getCabFromList(int cabLocation) {
        WebElement element = driver.findElement(By.xpath(String.format(CabProviderIds.CAB_PROVIDERS+"[%s]",cabLocation)));
        return element;
    }

    private boolean isOutBoundAvailable() {
        boolean outboundAvailable = false;
        try {
            outgoingCabs.getText();
            outboundAvailable = true;
        } catch (NoSuchElementException e) {

        }
        return outboundAvailable;
    }

    private boolean isReturnAvailable() {
        boolean returnAvailable = false;
        try {
            returnCabs.getText();
            returnAvailable = true;
        } catch (NoSuchElementException e) {

        }
        return returnAvailable;
    }

    private void setupCabDetails(WebElement lastCabProvider) {
        cabResultsModuleLogger.info("Setting up cab details...");
        providerTitle = updateElementFrom(CabProviderIds.PROVIDER_TITLE);
        if(providerTitle!=null)
            cabResultsModuleLogger.info(String.format("Choosen cab provider is %s", providerTitle.getText()));
        else
            cabResultsModuleLogger.info("Choosen cab provider is %s", providerTitle.getText());
        providerPrice = updateElementFrom(CabProviderIds.PROVIDER_PRICE);
        if(providerPrice!=null)
            cabResultsModuleLogger.info(String.format("Choosen cab price is %s",providerPrice.getText()));
        else
            cabResultsModuleLogger.info("Choosen cab has no price.");
        providerBase = updateElementFrom(CabProviderIds.PROVIDER_BASE);
        if(providerBase!=null)
            cabResultsModuleLogger.info(String.format("Choosen cab base is %s",providerBase.getText()));
        else
            cabResultsModuleLogger.info("Choosen cab has no base.");
        providerEmission = updateElementFrom(CabProviderIds.PROVIDER_EMISSION);
        if(providerEmission!=null)
            cabResultsModuleLogger.info(String.format("Choosen cab emission is %s",providerEmission.getText()));
        else
            cabResultsModuleLogger.info("Choosen cab has no emission.");
        cabDetailsSet = true;
    }

    private WebElement updateElementFrom(String id) {
       WebElement elementToUpdate = null;
        try {
            By by = buildElementXpath(id);
            List<WebElement> elements = driver.findElements(by);
            if(elements.size()>0) {
                elementToUpdate = elements.get(0);
                logger.info("Element value is "+elementToUpdate.getText());
            }

        }catch (Exception e) {
            //Handle as null
        }
        return elementToUpdate;
    }
    

    private WebElement getElementFrom(WebElement parentElement, By by) {
        WebElement elementToUpdate = null;
        try {
           elementToUpdate = parentElement.findElement(by);
        }catch (Exception e) {
            //Handle as null
        }
        return elementToUpdate;
    }


    private static class CabProviderIds {
        static String PROVIDER_TITLE = "providers_list_title_tv";
        static String PROVIDER_PRICE = "providers_list_price_tv";
        static String PROVIDER_BASE  = "cabProviderBasedTv";
        static String PROVIDER_EMISSION = "cabProviderEmissionTv";
        static String PROVIDER_PAYMENT_TYPE = "providers_list_payment_type";
        static String CAB_PROVIDERS = "//android.widget.ListView//android.widget.FrameLayout";
        static String RESOURCE_ID = "[@resource-id='com.minicabit.android:id/%s']";
    }

    private static class CabProviderBys {
        static By CAB_PROVIDERS = By.xpath("//android.widget.ListView//android.widget.FrameLayout");
    }

    private By buildElementXpath(String locatorId) {
        String root = String.format(CabProviderIds.CAB_PROVIDERS+"[%s]",cabLocation);
        String withResourceId = String.format(root+TEXT_VIEW+CabProviderIds.RESOURCE_ID,locatorId);
        return By.xpath(withResourceId);
    }

    private void addACabProviderToList(String text) {
        cabProviderAdded =  cabProvidersSet.add(text);
    }
}
