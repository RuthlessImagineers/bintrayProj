package entity.journeyDetails;

import interfaces.Journey;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public class ReturnJourney implements Journey {

    private String pickup;
    private String dropoff;
    private String pickupDate;
    private String pickupTime;
    private String returnDate;
    private String returnTime;
    private int passengers;

    public String getPickup() {
        return pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getReturnDate() { return returnDate; }

    public String getReturnTime() { return returnTime; }

    public int getPassengers() {
        return passengers;
    }

    public List<Via> getVias() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Get Vias is supported for only journeyWithVia");
    }
}
