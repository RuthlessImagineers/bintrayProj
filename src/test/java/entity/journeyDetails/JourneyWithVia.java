package entity.journeyDetails;

import interfaces.Journey;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public class JourneyWithVia implements Journey {

    private String pickup;
    private String dropoff;
    private String pickupDate;
    private String pickupTime;
    private int passengers;
    private List<Via> vias;


    @Override
    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    @Override
    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    @Override
    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    @Override
    public String getPickupTime() {
        return pickupTime;
    }

    @Override
    public String getReturnDate() throws OperationNotSupportedException {
        //TODO:
        return "";
    }

    @Override
    public String getReturnTime() throws OperationNotSupportedException {
        //TODO:
        return "";
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    @Override
    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public List<Via> getVias() {
        return vias;
    }

    public void setVias(List<Via> vias) {
        this.vias = vias;
    }


}
