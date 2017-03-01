package pages;

import enums.Attribute;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BasePage {

    private AppiumDriver driver;
    private WebDriverWait wait;
    protected PageStore pageStore;
    protected utils.dataUtils.DataMapper dataMapper = new utils.dataUtils.DataMapper();
    protected utils.dataUtils.EnumMapper enumMapper = new utils.dataUtils.EnumMapper();
    protected utils.dataUtils.DataFactory dataFactory = new utils.dataUtils.DataFactory();
    protected static Logger logger = LoggerFactory.getLogger(BasePage.class);
    private boolean fileFound;
    private String fileAbsPath;
    @FindBy(xpath = "//android.widget.ImageButton[@content-desc = 'Navigate up']")
    protected WebElement backButton;
    public BasePage() throws Exception {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, 90);
        pageStore = PageStore.getInstance();
    }

    public boolean allowPermissionPopup() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        try {
            WebElement acceptElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='Allow']")));
            acceptElement.click();
            return true;
        } catch (TimeoutException e) {
        }
        return false;
    }

    public void waitForElementToBeVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForElementToBeVisible(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {

        }
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeSelected(WebElement element) {
        wait.until(ExpectedConditions.elementSelectionStateToBe(element, true));
    }

    public void waitForElementToBeRefreshed(WebElement element) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
    }

    public void waitForElementToBeRefreshed(By by) {
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(by)));
    }

    public void waitForElementToBeClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitForPresenceOfElement(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForPresenceOfAllElements(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public boolean waitForTextToBePresentInElement(By by, String text) {
        try {
            if (wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text)))
                return true;
        } catch (TimeoutException e) {
            return false;
        }
        return false;
    }

    public void waitForInvisibilityOfElementByText(By by, String text) {
        wait.until(ExpectedConditions.invisibilityOfElementWithText(by, text));
    }

    public void waitForElementToBeInvisible(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void waitForElementToBeChecked(WebElement element) {
        wait.until(elementToBeChecked(element));
    }

    private static ExpectedCondition<WebElement> elementToBeChecked(
            final WebElement element) {
        return new ExpectedCondition<WebElement>() {

            public ExpectedCondition<WebElement> visibilityOfElement =
                    ExpectedConditions.visibilityOf(element);

            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = visibilityOfElement.apply(driver);
                try {
                    if (element != null && element.getAttribute("checked").equals("true")) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return "element to be checked : " + element;
            }
        };
    }

    public void tapOnBackButton() {
        waitForElementToBeClickable(backButton);
        backButton.click();
    }

    public void sendKeys(WebElement elem, String text) {
        waitForElementToBeClickable(elem);
        elem.click();
        if (text != null) {

            elem.sendKeys(text);
        } else {
            Assert.assertNotNull(elem.getText());
        }
        driver.getKeyboard();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (WebDriverException e) {
        }
    }

//    public void scrollTo(String text) {
//        try {
//            driver.scrollTo(text);
//        } catch (WebDriverException e) {
//
//        }
//    }

    public void scrollTo(String text) {
        scrollDownTo(text);
    }

    public void scrollDownTo(String text) {
        scrollDownTo(By.xpath("//*[@text=\"" + text + "\"]"));
    }

    public void scrollDownTo(By by) {
        int i = 0;
        while (i < 5) {
            if (driver.findElements(by).size() > 0)
                return;

            int height = driver.manage().window().getSize().getHeight();
            int width = driver.manage().window().getSize().getWidth();

            driver.swipe(width / 2, height * 2 / 3, width / 2, height / 3, 1000);

            i++;
        }
        Assert.fail("Did not find : " + by.toString());
    }

    public void scrollUpTo(String text) {
        scrollUpTo(By.xpath("//*[@text=\"" + text + "\"]"));
    }

    public void scrollUpTo(By by) {
        int i = 0;
        while (i < 5) {
            if (driver.findElements(by).size() > 0)
                return;

            int height = driver.manage().window().getSize().getHeight();
            int width = driver.manage().window().getSize().getWidth();

            driver.swipe(width / 2, height / 3, width / 2, height * 2 / 3, 1000);

            i++;
        }
        Assert.fail("Did not find : " + by.toString());
    }

    protected void swipeFromTo(WebElement startElement, WebElement stopElement) {
        driver.swipe(startElement.getLocation().getX(), startElement.getLocation().getY(), stopElement.getLocation().getX(), stopElement.getLocation().getY(), 1000);
    }

    public void swipeFromLeftToRight(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        int xAxisStartPoint = webElement.getLocation().getX();
        int xAxisEndPoint = xAxisStartPoint + webElement.getSize().getWidth();
        int yAxis = webElement.getLocation().getY();
        TouchAction act = new TouchAction(driver);
        System.out.print(xAxisStartPoint + " " + yAxis);
        act.longPress(xAxisStartPoint, yAxis).moveTo(xAxisEndPoint - 1, yAxis).release().perform();
    }


    public void swipeFromRightToLeft(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        int xAxisStartPoint = webElement.getLocation().getX();
        int xAxisEndPoint = xAxisStartPoint + webElement.getSize().getWidth();
        int yAxis = webElement.getLocation().getY();
        TouchAction act = new TouchAction(driver);
        System.out.print(xAxisStartPoint + " " + yAxis);
        act.press(xAxisEndPoint-1, yAxis).moveTo(xAxisStartPoint, yAxis).release().perform();
    }

    protected Integer getPriceFormat(String price) {
        String b = price.replaceAll("Rp. ", "").replaceAll("\\.", "").replaceAll("-", "");
        return Integer.parseInt(b);
    }


    protected void bringElementIntoView(WebElement webElement) {
        TouchAction action = new TouchAction(driver);
        action.moveTo(webElement.getLocation().getX(), webElement.getLocation().getY())
                .release().perform();
    }


    public void navigateBack() {
        driver.navigate().back();
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected <T> void refreshElements(T obj) {
        PageFactory.initElements(driver, obj);
    }

    protected <T> void refreshAppiumElements(T obj) {
        PageFactory.initElements(new AppiumFieldDecorator(driver),obj);
    }

    protected void selectOption(WebElement element, String option) {
        Select select = new Select(element);
        select.selectByVisibleText(option);
    }

    protected void enterValue(WebElement element, String valueToEnter) {
        enterValue(element,valueToEnter,false);
    }

    protected void enterValue(WebElement element, String valueToEnter, boolean clearValue) {
        if(clearValue)
            element.clear();
        element.sendKeys(valueToEnter);
    }

    protected boolean getStateForAttribute(WebElement element, Attribute attribute) {
        String attrValue = element.getAttribute(attribute.name().toLowerCase());
        return Boolean.getBoolean(attrValue);
    }


    protected void moveElementToThisElementPos(WebElement parentElement, WebElement childElement) {
        int endPosX = parentElement.getLocation().getX();
        int startPostX = childElement.getLocation().getX();
        int startPostY = childElement.getLocation().getY();
        int endPosY = parentElement.getLocation().getY();
        driver.swipe(startPostX,startPostY,endPosX,endPosY,0);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getValidDataFilePath(String rootFolderPath, String fileName) {
        File rootFolder = new File(rootFolderPath);
        File[] files = rootFolder.listFiles();
        while (!fileFound) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getValidDataFilePath(file.getAbsolutePath(), fileName);
                }
                if (file.getName().equals(fileName+Constants.Extenstions.JSON)) {
                    fileAbsPath = file.getAbsolutePath();
                    fileFound = true;
                    break;
                }
            }
            break;
        }
        return fileAbsPath;
    }

    protected void selectValueFromDropDown(String value) {
        logger.info(String.format("Selecting value -- %s",value));
        List<WebElement> valueTypes = driver.findElements(By.id("text1"));
        for(WebElement valueType : valueTypes) {
            String text = valueType.getText();
            if(text.equals(value)) {
                valueType.click();
                break;
            }
        }
    }

    protected String getRandomEmailValue() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        String randomString = randomStringUtils.randomAlphanumeric(5);
        return randomString;
    }

    protected void clickNearby(WebElement element) {
        TouchAction action = new TouchAction(driver);
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        int midpos = width/2;
        int midPos1 = height/2;
        //action.press(midpos,midPos1).release().perform();
        action.tap(element);
    }
}
