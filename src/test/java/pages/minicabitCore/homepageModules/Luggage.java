package pages.minicabitCore.homepageModules;

import entity.journeyDetails.LuggageMaster;
import exceptions.LuggageException;
import io.appium.java_client.TouchAction;
import org.atteo.evo.inflector.English;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.minicabitCore.Homepage;
import pages.minicabitCore.homepageModules.luggageModules.*;
import utils.luggageUtils.LuggagePreview;
import utils.luggageUtils.LuggageSelection;

import static utils.Constants.GroupNames.*;
import static utils.Constants.DataFileNames.*;
import static org.junit.Assert.*;

import java.util.*;

public class Luggage extends Homepage {

    private LuggageMaster luggageMaster;
    private LuggageSelection luggageSelection;
    private int passengers;
    protected WebElement seekBarElement, luggageGroupItemCount;

    @FindBy(id = "luggageGroupNameTv")
    private List<WebElement> luggageGroupNames;

    @FindBy(id = "action_luggage_accept")
    private WebElement luggageAccept;


    protected static Map<String,String> previewMap = new HashMap<>();

    public Luggage() throws Exception {
        luggageMaster = (LuggageMaster) dataMapper.mapDetails(LuggageMaster.class,LUGGAGE_DETAILS);
    }

    public Luggage chooseLuggage(String luggage, int passengers) throws LuggageException {
        luggageSelection = new LuggageSelection(luggage);
        if(luggageSelection.isUserBringingLuggage())
            chooseActualLuggage(passengers);
        if(luggageSelection.isUserBringingOutdoorItems())
            chooseOutdoorItems(passengers);
        if(luggageSelection.isUserBringingChildItems())
            chooseChildItems(passengers);
        if(luggageSelection.isUserBringingBoxes())
            chooseBoxes(passengers);
        if(luggageSelection.isUserBringingFoldedWheelChair())
            chooseFoldedWheelChair(passengers);
        if(luggageSelection.isUserBringingPets())
            choosePets(passengers);
        return this;
    }

    public Luggage verifyLuggagePreview() {
        logger.info("Verifying if the luggage selected matches the luggage previewed");
        List<WebElement> luggagePreviewName = driver.findElements(By.id("luggage_preview_type_tv"));
        List<WebElement> luggagePreviewCount = driver.findElements(By.id("luggage_preview_count_tv"));
        LuggagePreview preview = new LuggagePreview(luggagePreviewName,luggagePreviewCount);
        Map<String, String> luggagePreviewMap = preview.mapGroupPreviewNameAndCount();
        logger.info("Preview Map -- "+previewMap);
        logger.info("Luggage preview map -- "+luggagePreviewMap);
        assertEquals(previewMap.equals(luggagePreviewMap),true);
        return this;
    }

    public Homepage acceptLuggage() {
        waitForElementToBeClickable(luggageAccept);
        luggageAccept.click();
        return pageStore.get(Homepage.class);
    }

    private void chooseActualLuggage(int passengers) throws LuggageException {
        logger.info("Choosing luggage for travel");
        WebElement luggageElement = getElement(LUGGAGE);
        luggageElement.click();
        pageStore.get(BringingLuggage.class).selectLuggage(luggageMaster.getLuggage(), passengers);
        luggageElement.click();
    }

    private void chooseOutdoorItems(int passengers) throws LuggageException {
        logger.info("Choosing outdoor items for travel");
        WebElement outdoorItemsElement = getElement(OUTDOOR_ITEMS);
        outdoorItemsElement.click();
        pageStore.get(BringingOutdoorItems.class).selectOutDoorItems(luggageMaster.getOutdoorItems(), passengers);
        outdoorItemsElement.click();
    }

    private void chooseChildItems(int passengers) {
        logger.info("Choosing child items for travel");
        WebElement childItemsElement = getElement(CHILD_ITEMS);
        childItemsElement.click();
        pageStore.get(BringingChildItems.class).selectChildItems(passengers);
        childItemsElement.click();
    }

    private void chooseBoxes(int passengers) {
        logger.info("Choosing boxes for travel");
        WebElement boxesElement = getElement(BOXES);
        boxesElement.click();
        pageStore.get(BringingBoxes.class).selectBoxes(passengers);
        boxesElement.click();
    }

    private void chooseFoldedWheelChair(int passengers) {
        logger.info("Choosing folded wheel chair for travel");
        WebElement foldedWheelChair = getElement(FOLDED_WHEELCHAIR);
        foldedWheelChair.click();
        pageStore.get(BringingFoldedWheelChair.class).selectFoldedWheelChair(passengers);
        foldedWheelChair.click();
    }

    private void choosePets(int passengers) {
        logger.info("Choosing pets for travel");
        WebElement petsElement = getElement(PETS);
        petsElement.click();
        pageStore.get(BringingPets.class).selectPets(passengers);
        petsElement.click();
    }


    private WebElement getElement(String elementText) {
        WebElement desiredElement = null;
        for(WebElement groupName : luggageGroupNames)  {
            if(groupName.getText().equals(elementText)) {
                desiredElement = groupName;
            }
        }
        return desiredElement;
    }

    public int getTotalLuggage() {
        int totalLuggage = 0;

        Set<String> luggageCount = previewMap.keySet();
        Iterator<String> luggageIterator = luggageCount.iterator();
        while (luggageIterator.hasNext()) {
            String key = luggageIterator.next();
            String value = previewMap.get(key);
            totalLuggage = totalLuggage+Integer.parseInt(value);
        }
        return totalLuggage;

    }
    protected void seekNumberOfItems(int items, WebElement elementToDrag, int maxItemsAllowed) {
        int xStartingPoint = elementToDrag.getLocation().getX();
        int yStartingPoint = elementToDrag.getLocation().getY();
        int yEndingPoint = elementToDrag.getSize().getHeight();
        int xSeekWidth = elementToDrag.getSize().getWidth();
        int pointForOneItem = xSeekWidth/maxItemsAllowed;
        int xEndingPoint = pointForOneItem*items;
        TouchAction action = new TouchAction(driver);
        action.longPress(xStartingPoint,yStartingPoint)
                .moveTo(xEndingPoint,yStartingPoint).release().perform();
    }

    protected String convertToPlural(String singularString) {
        String baseString = "";
        if(singularString.contains("(")) {
            String[] splitter = singularString.split("\\(");
            String str1 = splitter[0].trim();
            String str2 = "("+splitter[1];
            str1 = English.plural(str1);
            baseString = str1+" "+str2;
        }
        return baseString;
    }

}

