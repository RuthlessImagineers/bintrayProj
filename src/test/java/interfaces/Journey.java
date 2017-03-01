package interfaces;

import entity.journeyDetails.Via;

import javax.naming.OperationNotSupportedException;
import java.util.List;


public interface Journey {
    String getPickup();
    String getDropoff();
    String getPickupDate();
    String getPickupTime();
    String getReturnDate() throws OperationNotSupportedException;
    String getReturnTime() throws OperationNotSupportedException;
    int    getPassengers();
    List<Via> getVias() throws OperationNotSupportedException;
}
