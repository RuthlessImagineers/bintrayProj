package entity.journeyDetails;

import entity.journeyDetails.luggageDetails.*;

/**
 * Created by krishnanand on 08/08/16.
 */
public class LuggageMaster {

    private BringingLuggage luggage;
    private OutdoorItems outdoorItems;
    private ChildItems childItems;
    private Boxes boxes;
    private HouseholdItems householdItems;
    private FoldedWheelChair foldedWheelChair;
    private Pets pets;

    public BringingLuggage getLuggage() {
        return luggage;
    }

    public void setLuggage(BringingLuggage luggage) {
        this.luggage = luggage;
    }

    public OutdoorItems getOutdoorItems() {
        return outdoorItems;
    }

    public void setOutdoorItems(OutdoorItems outdoorItems) {
        this.outdoorItems = outdoorItems;
    }

    public ChildItems getChildItems() {
        return childItems;
    }

    public void setChildItems(ChildItems childItems) {
        this.childItems = childItems;
    }

    public Boxes getBoxes() {
        return boxes;
    }

    public void setBoxes(Boxes boxes) {
        this.boxes = boxes;
    }

    public HouseholdItems getHouseholdItems() {
        return householdItems;
    }

    public void setHouseholdItems(HouseholdItems householdItems) {
        this.householdItems = householdItems;
    }

    public FoldedWheelChair getFoldedWheelChair() {
        return foldedWheelChair;
    }

    public void setFoldedWheelChair(FoldedWheelChair foldedWheelChair) {
        this.foldedWheelChair = foldedWheelChair;
    }

    public Pets getPets() {
        return pets;
    }

    public void setPets(Pets pets) {
        this.pets = pets;
    }
}
