package entity.journeyDetails;

import interfaces.Journey;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/**
 * Created by krishnanand on 27/07/16.
 */
public class SingleJourney implements Journey {


    private String pickup;
    private String dropoff;
    private String pickupDate;
    private String pickupTime;
    private int passengers;

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    @Override
    public String getReturnDate() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Return date is not valid for single journey.");
    }

    @Override
    public String getReturnTime() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Return time is not valid for single journey");
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getPassengers() { return passengers; }

    public void setPassengers(int passengers) { this.passengers = passengers; }

    @Override
    public List<Via> getVias() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Get vias is supported only for journeyWithVia");
    }
}
