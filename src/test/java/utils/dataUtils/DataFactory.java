package utils.dataUtils;

import entity.JourneyDetails;
import entity.TripDetails;
import interfaces.Journey;
import entity.journeyDetails.JourneyWithVia;
import entity.journeyDetails.ReturnJourney;
import entity.journeyDetails.SingleJourney;
import enums.TripType;

/**
 * Created by krishnanand on 27/07/16.
 */
public class DataFactory {


    public Journey getJourneyType(JourneyDetails journeyDetails, TripType tripType) {

        Journey journey = null;
        switch (tripType) {
            case SINGLEJOURNEY:
                journey = journeyDetails.getSingleJourney();
            break;
            case RETURNJOURNEY:
                journey = journeyDetails.getReturnJourney();
            break;
            case JOURNEYWITHVIA:
                journey = journeyDetails.getJourneyWithVia();
        }
        return journey;
    }
}
