package entity;

import entity.journeyDetails.Via;
import entity.journeyDetails.luggageDetails.BringingLuggage;
import entity.searchResults.Cab;
import enums.TripType;
import interfaces.Journey;

import java.util.Date;
import java.util.List;

public class TripDetails {
    private Location location;
    private Date pickupDate;
    private Date returnDate;
    private String pickupTime;
    private String returnTime;
    private Cab cab;
    private Cab returnCab;
    private int passengers;
    private int luggage;
    private boolean withLuggage;
    private List<Via> vias;
    private TripType tripType;
    private Journey journey;
    private String totalPrice = "";

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Cab getCab() { return cab; }

    public void setCab(Cab cab) { this.cab = cab; }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getLuggage() {
        return luggage;
    }

    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    public boolean isWithLuggage() {
        return withLuggage;
    }

    public void setWithLuggage(boolean withLuggage) {
        this.withLuggage = withLuggage;
    }

    public List<Via> getVias() { return vias; }

    public void setVias(List<Via> vias) { this.vias = vias; }

    public TripType getTripType() { return tripType; }

    public void setTripType(TripType tripType) { this.tripType = tripType; }

    public Journey getJourney() { return journey; }

    public void setJourney(Journey journey) { this.journey = journey; }

    public Date getReturnDate() { return returnDate; }

    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    public String getReturnTime() { return returnTime; }

    public void setReturnTime(String returnTime) { this.returnTime = returnTime; }

    public Cab getReturnCab() { return returnCab; }

    public void setReturnCab(Cab returnCab) { this.returnCab = returnCab; }

    public String getTotalPrice() { return totalPrice; }

    public void setTotalPrice(String totalPrice) { this.totalPrice = totalPrice; }
}
