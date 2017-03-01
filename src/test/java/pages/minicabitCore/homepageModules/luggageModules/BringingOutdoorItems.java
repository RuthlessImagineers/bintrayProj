package pages.minicabitCore.homepageModules.luggageModules;

import ch.lambdaj.function.aggregate.Max;
import entity.journeyDetails.LuggageMaster;
import entity.journeyDetails.luggageDetails.OutdoorItems;
import exceptions.LuggageException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.minicabitCore.homepageModules.Luggage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Constants.GroupNames.Outdoor.*;


public class BringingOutdoorItems extends Luggage {

    private Logger outDoorLogger;
    private Map<Integer,MaxOutdoorLuggage> outdoorMap;
    private int elementCounter = 0;
    private OutdoorItems outdoorItems;
    private String elementText;

    @FindBy(id = "luggageGroupItemName")
    private List<WebElement> groupItemNames;

    @FindBy(id = "luggageSeekBar")
    private List<WebElement> luggageSeekBar;

    @FindBy(id = "luggageGroupItemValue")
    private List<WebElement> luggageGroupItemValue;

    public BringingOutdoorItems() throws Exception {
        outDoorLogger = LoggerFactory.getLogger(this.getClass());
        outdoorMap = this.new OutdoorMapper().getOutdoorMaxMap();
    }

    public void selectOutDoorItems(OutdoorItems outdoorItems, int passengers) throws LuggageException {
        this.outdoorItems = outdoorItems;
        MaxOutdoorLuggage outdoorLuggage = outdoorMap.get(passengers);
        selectTent(outdoorLuggage.getTent());
        selectCampingBag(outdoorLuggage.getCampingBag());
        selectPairOfSkis(outdoorLuggage.getPairOfSkis());
        selectSnowboard(outdoorLuggage.getSnowBoard());
        selectBike(outdoorLuggage.getBike());
        selectBikeBox(outdoorLuggage.getBikeBox());
        selectGolfBag(outdoorLuggage.getGolfBag());

    }

    public void selectTent(int maxLuggage) throws LuggageException {
        int tentCount = outdoorItems.getTent();
        selectDesiredLuggage(tentCount,maxLuggage,TENT);
    }

    public void selectCampingBag(int maxLuggage) throws LuggageException {
        int campingBag = outdoorItems.getCampingBag();
        selectDesiredLuggage(campingBag,maxLuggage,CAMPING_BAG);
    }

    public void selectPairOfSkis(int maxLuggage) throws LuggageException {
        int pairOfSkis = outdoorItems.getPairOfSkis();
        selectDesiredLuggage(pairOfSkis,maxLuggage,PAIR_OF_SKIS);
    }

    public void selectSnowboard(int maxLuggage) throws LuggageException {
        int snowboards = outdoorItems.getSnowBoard();
        selectDesiredLuggage(snowboards,maxLuggage,SNOWBOARD);
    }

    public void selectBike(int maxLuggage) throws LuggageException{
        int bike = outdoorItems.getBike();
        selectDesiredLuggage(bike,maxLuggage,BIKE);
    }

    public void selectBikeBox(int maxLuggage) throws LuggageException {
        int bikeBox = outdoorItems.getBikeBox();
        selectDesiredLuggage(bikeBox,maxLuggage,BIKE_BOX);
    }

    public void selectGolfBag(int maxLuggage)  throws LuggageException{
        int golfBag = outdoorItems.getGolfBag();
        selectDesiredLuggage(golfBag,maxLuggage,GOLF_BAG);
    }

    private void selectDesiredLuggage(int luggageCount, int maxLuggage,String luggageType) throws LuggageException {
        if(luggageCount>0) {
            logger.info("Choosing luggage "+luggageType);
            if(luggageCount<=maxLuggage) {
                WebElement element = getElement(luggageType);
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

    private String formatElementText(String textToFormat,int count) {
        if(count>1) {
            textToFormat = textToFormat.replace("Tent","Tents").replace("bag","bags").replace("Pair","Pairs")
                    .replace("Snowboard","Snowboards").replace("Bike","Bikes").replace("box","boxes").replace("bag","bags");
        }
        return textToFormat;
    }

    private class OutdoorMapper {
        private Map<Integer,MaxOutdoorLuggage> outdoorMaxMap = new HashMap<>();

        public OutdoorMapper() {
            mapOutdoor();
        }

        private void mapOutdoor() {
            outdoorMaxMap.put(1,new MaxOutdoorLuggage(5,11,2,3,2,2,7));
            outdoorMaxMap.put(2,new MaxOutdoorLuggage(4,10,2,2,2,2,7));
            outdoorMaxMap.put(3,new MaxOutdoorLuggage(4,9,2,2,1,1,6));
            outdoorMaxMap.put(4,new MaxOutdoorLuggage(4,8,2,2,1,1,6));
            outdoorMaxMap.put(5,new MaxOutdoorLuggage(3,8,2,2,1,1,5));
            outdoorMaxMap.put(6,new MaxOutdoorLuggage(3,7,2,2,1,1,5));
            outdoorMaxMap.put(7,new MaxOutdoorLuggage(3,6,1,1,1,1,4));
            outdoorMaxMap.put(8,new MaxOutdoorLuggage(2,5,1,1,1,1,4));
        }

        public Map<Integer,MaxOutdoorLuggage> getOutdoorMaxMap() {
            return outdoorMaxMap;
        }

    }

    protected class MaxOutdoorLuggage {
        int tent;
        int campingBag;
        int pairOfSkis;
        int snowBoard;
        int bike;
        int bikeBox;
        int golfBag;


        public MaxOutdoorLuggage(int tent, int campingBag, int pairOfSkis, int snowBoard, int bike, int bikeBox, int golfBag) {
            setTent(tent);
            setCampingBag(campingBag);
            setPairOfSkis(pairOfSkis);
            setSnowBoard(snowBoard);
            setBike(bike);
            setBikeBox(bikeBox);
            setGolfBag(golfBag);
        }

        public int getTent() {
            return tent;
        }

        public void setTent(int tent) {
            this.tent = tent;
        }

        public int getCampingBag() {
            return campingBag;
        }

        public void setCampingBag(int campingBag) {
            this.campingBag = campingBag;
        }

        public int getPairOfSkis() {
            return pairOfSkis;
        }

        public void setPairOfSkis(int pairOfSkis) {
            this.pairOfSkis = pairOfSkis;
        }

        public int getSnowBoard() {
            return snowBoard;
        }

        public void setSnowBoard(int snowBoard) {
            this.snowBoard = snowBoard;
        }

        public int getBike() {
            return bike;
        }

        public void setBike(int bike) {
            this.bike = bike;
        }

        public int getBikeBox() {
            return bikeBox;
        }

        public void setBikeBox(int bikeBox) {
            this.bikeBox = bikeBox;
        }

        public int getGolfBag() {
            return golfBag;
        }

        public void setGolfBag(int golfBag) {
            this.golfBag = golfBag;
        }
    }
}
