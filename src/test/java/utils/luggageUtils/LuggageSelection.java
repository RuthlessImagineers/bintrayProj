package utils.luggageUtils;

import static utils.Constants.GroupNames.*;

/**
 * Created by krishnanand on 08/08/16.
 */
public class LuggageSelection {
    private String luggageGroupList;
    private boolean isUserBringingLuggage;
    private boolean isUserBringingOutdoorItems;
    private boolean isUserBringingChildItems;
    private boolean isUserBringingBoxes;
    private boolean isUserBringingHouseholdItems;
    private boolean isUserBringingFoldedWheelChair;
    private boolean isUserBringingPets;

    public LuggageSelection(String luggageGroupList) {
        this.luggageGroupList = luggageGroupList;
        setUp();
    }

    public LuggageSelection setUp() {
        if(luggageGroupList.contains(LUGGAGE))
            setUserBringingLuggage(true);
        if(luggageGroupList.contains(OUTDOOR_ITEMS))
            setUserBringingOutdoorItems(true);
        if(luggageGroupList.contains(CHILD_ITEMS))
            setUserBringingChildItems(true);
        if(luggageGroupList.contains(BOXES))
            setUserBringingBoxes(true);
        if(luggageGroupList.contains(HOUSEHOLD_ITEMS))
            setUserBringingHouseholdItems(true);
        if(luggageGroupList.contains(FOLDED_WHEELCHAIR))
            setUserBringingFoldedWheelChair(true);
        if (luggageGroupList.contains(PETS))
            setUserBringingPets(true);
        return this;
    }

    public boolean isUserBringingLuggage() {
        return isUserBringingLuggage;
    }

    private void setUserBringingLuggage(boolean userBringingLuggage) {
        isUserBringingLuggage = userBringingLuggage;
    }

    public boolean isUserBringingOutdoorItems() {
        return isUserBringingOutdoorItems;
    }

    private void setUserBringingOutdoorItems(boolean userBringingOutdoorItems) {
        isUserBringingOutdoorItems = userBringingOutdoorItems;
    }

    public boolean isUserBringingChildItems() {
        return isUserBringingChildItems;
    }

    private void setUserBringingChildItems(boolean userBringingChildItems) {
        isUserBringingChildItems = userBringingChildItems;
    }

    public boolean isUserBringingBoxes() {
        return isUserBringingBoxes;
    }

    private void setUserBringingBoxes(boolean userBringingBoxes) {
        isUserBringingBoxes = userBringingBoxes;
    }

    public boolean isUserBringingHouseholdItems() {
        return isUserBringingHouseholdItems;
    }

    private void setUserBringingHouseholdItems(boolean userBringingHouseholdItems) {
        isUserBringingHouseholdItems = userBringingHouseholdItems;
    }

    public boolean isUserBringingFoldedWheelChair() {
        return isUserBringingFoldedWheelChair;
    }

    private void setUserBringingFoldedWheelChair(boolean userBringingFoldedWheelChair) {
        isUserBringingFoldedWheelChair = userBringingFoldedWheelChair;
    }

    public boolean isUserBringingPets() {
        return isUserBringingPets;
    }

    private void setUserBringingPets(boolean userBringingPets) {
        isUserBringingPets = userBringingPets;
    }


}
