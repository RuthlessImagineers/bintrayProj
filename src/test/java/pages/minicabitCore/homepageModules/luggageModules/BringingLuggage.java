package pages.minicabitCore.homepageModules.luggageModules;

import exceptions.LuggageException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.minicabitCore.Homepage;
import pages.minicabitCore.homepageModules.Luggage;
import utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Constants.GroupNames.Luggage.*;

public class BringingLuggage extends Luggage {

    @FindBy(id = "luggageGroupItemName")
    private List<WebElement> groupItemNames;

    @FindBy(id = "luggageSeekBar")
    private List<WebElement> luggageSeekBar;

    @FindBy(id = "luggageGroupItemValue")
    private List<WebElement> luggageGroupItemValue;

    private Logger logger;
    private int elementCounter = 0;
    private Map<Integer,MaxLuggage> luggageMap;
    private entity.journeyDetails.luggageDetails.BringingLuggage bringingLuggage;
    private String elementText;

    public BringingLuggage() throws Exception {
        this.logger = LoggerFactory.getLogger(this.getClass());
        luggageMap = this.new LuggageMapper().getLuggageMaxMap();
    }

    public void selectLuggage(entity.journeyDetails.luggageDetails.BringingLuggage bringingLuggage, int forPassengers) throws LuggageException {
        this.bringingLuggage = bringingLuggage;
        MaxLuggage maxLuggage = luggageMap.get(forPassengers);
        selectSmallSuitcase(maxLuggage.getSmallSuitcase());
        selectMediumSuitcase(maxLuggage.getMediumSuitcase());
        selectLargeSuitcase(maxLuggage.getLargeSuitcase());
        selectBackpack(maxLuggage.getBackpack());
    }

    public void selectSmallSuitcase(int maxLuggage) throws LuggageException {
        int smallSuitcaseCount = bringingLuggage.getSmallSuitcase();
        selectDesiredLuggage(smallSuitcaseCount,maxLuggage,SMALL_SUITCASE);
    }

    public void selectMediumSuitcase(int maxLuggage) throws LuggageException {
        int mediumSuitcaseCount = bringingLuggage.getMediumSuitcase();
        selectDesiredLuggage(mediumSuitcaseCount,maxLuggage,MEDIUM_SUITCASE);
    }

    public void selectLargeSuitcase(int maxLuggage) throws LuggageException {
        int largeSuitcaseCount = bringingLuggage.getLargeSuitcase();
        selectDesiredLuggage(largeSuitcaseCount,maxLuggage,LARGE_SUITCASE);
    }

    public void selectBackpack(int maxLuggage) throws LuggageException {
        int backpackCount = bringingLuggage.getBackpack();
        selectDesiredLuggage(backpackCount,maxLuggage,BACKPACK);
    }


    private void selectDesiredLuggage(int luggageCount, int maxLuggage,String luggageType) throws LuggageException {
        if(luggageCount>0) {
            logger.info("Choosing luggage "+luggageType);
            if(luggageCount<=maxLuggage) {
                WebElement element = getElement(MEDIUM_SUITCASE);
                seekNumberOfItems(luggageCount, seekBarElement, maxLuggage);

                if(luggageCount>1) {
                   elementText = convertToPlural(element.getText());
                }
                previewMap.put(elementText,luggageGroupItemCount.getText());
            }else {
                throw new LuggageException(String.format("You have choosen %s as %s, " +
                        "but the maximum allowed luggage for the passengers selected is only %s",luggageType,luggageCount,maxLuggage));
            }
        }
    }

    private WebElement getElement(String luggageType) {
        WebElement desiredElement = null;
        for(WebElement luggageGroupItem : groupItemNames) {
            if(luggageGroupItem.getText().contains(luggageType)) {
                desiredElement = luggageGroupItem;
                seekBarElement = luggageSeekBar.get(elementCounter);
                luggageGroupItemCount = luggageGroupItemValue.get(elementCounter);
                break;
            }
            elementCounter++;
        }
        elementCounter =0;
        return desiredElement;
    }


    private WebElement getSeekBarElement() {
        return seekBarElement;
    }


    private class LuggageMapper {
        private Map<Integer,MaxLuggage> luggageMaxMap = new HashMap<>();

        public LuggageMapper() {
            mapLuggage();
        }

        private void mapLuggage() {
            luggageMaxMap.put(1,new MaxLuggage(15,11,11,15));
            luggageMaxMap.put(2,new MaxLuggage(14,11,10,14));
            luggageMaxMap.put(3,new MaxLuggage(13,10,9,13));
            luggageMaxMap.put(4,new MaxLuggage(12,9,8,12));
            luggageMaxMap.put(5,new MaxLuggage(11,8,8,11));
            luggageMaxMap.put(6,new MaxLuggage(10,8,7,10));
            luggageMaxMap.put(7,new MaxLuggage(9,7,6,9));
            luggageMaxMap.put(8,new MaxLuggage(8,6,5,8));
        }

        public Map<Integer,MaxLuggage> getLuggageMaxMap() {
            return luggageMaxMap;
        }

    }

    protected class MaxLuggage {
        int smallSuitcase;
        int mediumSuitcase;
        int largeSuitcase;
        int backpack;

        public MaxLuggage(int smallSuitcase,int mediumSuitcase, int largeSuitcase, int backpack) {
            setSmallSuitcase(smallSuitcase);
            setMediumSuitcase(mediumSuitcase);
            setLargeSuitcase(largeSuitcase);
            setBackpack(backpack);
        }

        public int getSmallSuitcase() {
            return smallSuitcase;
        }

        public void setSmallSuitcase(int smallSuitcase) {
            this.smallSuitcase = smallSuitcase;
        }

        public int getMediumSuitcase() {
            return mediumSuitcase;
        }

        public void setMediumSuitcase(int mediumSuitcase) {
            this.mediumSuitcase = mediumSuitcase;
        }

        public int getLargeSuitcase() {
            return largeSuitcase;
        }

        public void setLargeSuitcase(int largeSuitcase) {
            this.largeSuitcase = largeSuitcase;
        }

        public int getBackpack() {
            return backpack;
        }

        public void setBackpack(int backpack) {
            this.backpack = backpack;
        }
    }
}
