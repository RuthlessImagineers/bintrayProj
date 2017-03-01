package entity;

import java.util.Date;

/**
 * Created by krishnanand on 25/07/16.
 */
public class From {

    private String pickupLocation;
    private Date pickupDate;
    private String pickupHours;
    private String pickupMinutes;

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupHours() {
        return pickupHours;
    }

    /**
     * <p>Users of minicabit can select any pickup hour in 24 hour format. Starting 00 to 23 hours</p>
     * @param pickupHours
     */
    public void setPickupHours(String pickupHours) {
        this.pickupHours = pickupHours;
    }

    public String getPickupMinutes() {
        return pickupMinutes;
    }

    public void setPickupMinutes(String pickupMinutes) {
        this.pickupMinutes = pickupMinutes;
    }
}
