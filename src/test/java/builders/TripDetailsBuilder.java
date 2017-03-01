package builders;

import entity.From;
import entity.Location;
import entity.To;
import entity.TripDetails;
import entity.journeyDetails.Via;
import entity.journeyDetails.luggageDetails.BringingLuggage;
import entity.searchResults.Cab;
import enums.PaymentType;
import enums.TripType;
import exceptions.MaximumPassengersReachedException;
import interfaces.Journey;

import java.util.List;

import static utils.Constants.*;


public class TripDetailsBuilder extends Builder {

    TripDetails tripDetails = pageStore.get(TripDetails.class);



    public TripDetailsBuilder() throws Exception {
        From pickup = new FromBuilder().build();
        To dropOff = new ToBuilder().build();
        Location location = new Location(pickup,dropOff);
        tripDetails.setLocation(location);
        tripDetails.setPickupDate(getTodaysDate());
        tripDetails.setPickupTime("20:30");
        tripDetails.setCab(new CabBuilder().build());
        tripDetails.setPassengers(2);
        tripDetails.setLuggage(8);
    }


    public TripDetailsBuilder withLocation(Location location) {
        tripDetails.setLocation(location);
        return this;
    }

    public TripDetailsBuilder withPickupDate(String date) {

        if (date.trim().equalsIgnoreCase("tomorrow")) {
            tripDetails.setPickupDate(getTomorrowsDate());
        } else if(date.trim().equalsIgnoreCase("yesterday")) {
            tripDetails.setPickupDate(getYesterdaysDate());
        } else if(date.trim().equalsIgnoreCase("today")) {
            tripDetails.setPickupDate(getTodaysDate());
        }
        return this;
    }

    public TripDetailsBuilder withPickupTime(String pickupTime) {
        tripDetails.setPickupTime(pickupTime);
        return this;
    }

    public TripDetailsBuilder withReturnDate(String date) {
        if (date.trim().equalsIgnoreCase("tomorrow")) {
            tripDetails.setReturnDate(getTomorrowsDate());
        } else if(date.trim().equalsIgnoreCase("yesterday")) {
            tripDetails.setReturnDate(getYesterdaysDate());
        } else if(date.trim().equalsIgnoreCase("today")) {
            tripDetails.setReturnDate(getTodaysDate());
        }
        return this;
    }

    public TripDetailsBuilder withReturnTime(String returnTime) {
        tripDetails.setReturnTime(returnTime);
        return this;
    }

    public TripDetailsBuilder withTripTotalPrice(Cab onboundCab, Cab returnCab) {
        String pickupPrice = onboundCab.getCabProviderPrice();
        String returnPrice = returnCab==null?"0.00":returnCab.getCabProviderPrice();
        tripDetails.setTotalPrice(getTotalPrice(pickupPrice,returnPrice));
        return this;
    }

    public TripDetailsBuilder withTripTotalPrice(Cab onboundCab) {
        String pickupPrice = onboundCab.getCabProviderPrice();
        tripDetails.setTotalPrice(getTotalPrice(pickupPrice));
        return this;
    }


    public TripDetailsBuilder withCab(Cab cab) {
        tripDetails.setCab(cab);
        return this;
    }

    public TripDetailsBuilder withReturnCab(Cab cab) {
        tripDetails.setReturnCab(cab);
        return this;
    }

    public TripDetailsBuilder withPassengers(int passengers) throws MaximumPassengersReachedException {
        if(passengers<0||passengers>8)
            throw new MaximumPassengersReachedException(String.format("Maximum number of passengers " +
                    "allowed per booking is %s",MAXIMUM_PASSENGERS));

        tripDetails.setPassengers(passengers);
        return this;
    }

    public TripDetailsBuilder withVias(List<Via> vias) {
        tripDetails.setVias(vias);
        return this;
    }

    public TripDetailsBuilder withTripType(TripType tripType) {
        tripDetails.setTripType(tripType);
        return this;
    }

    public TripDetailsBuilder withJourney(Journey journey) {
        tripDetails.setJourney(journey);
        return this;
    }

    public TripDetails build() {
        return tripDetails;
    }
}
