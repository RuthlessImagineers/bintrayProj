package entity;

import interfaces.Journey;
import entity.journeyDetails.JourneyWithVia;
import entity.journeyDetails.ReturnJourney;
import entity.journeyDetails.SingleJourney;

public class Location {

    private String pickup;

    private String dropoff;

    public Location(String pickup, String dropoff) {
        setPickup(pickup);
        setDropoff(dropoff);
    }

    public Location(From pickup, To dropoff) {
        setPickup(pickup.getPickupLocation());
        setDropoff(dropoff.getDropOffLocation());
    }

    public Location(Journey journey) {
        if (journey instanceof SingleJourney) {
            setPickup(journey.getPickup());
            setDropoff(journey.getDropoff());
        } else if (journey instanceof ReturnJourney) {
            setPickup(journey.getPickup());
            setDropoff(journey.getDropoff());
        } else if (journey instanceof JourneyWithVia) {
            setPickup(journey.getPickup());
            setDropoff(journey.getDropoff());
        }
    }

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
}
