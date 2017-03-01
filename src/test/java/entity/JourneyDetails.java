package entity;

import entity.journeyDetails.JourneyWithVia;
import entity.journeyDetails.ReturnJourney;
import entity.journeyDetails.SingleJourney;

/**
 * Created by krishnanand on 27/07/16.
 */
public class JourneyDetails {

    private SingleJourney singleJourney;
    private ReturnJourney returnJourney;
    private JourneyWithVia journeyWithVia;

    public SingleJourney getSingleJourney() {
        return singleJourney;
    }

    public void setSingleJourney(SingleJourney singleJourney) {
        this.singleJourney = singleJourney;
    }

    public ReturnJourney getReturnJourney() {
        return returnJourney;
    }

    public void setReturnJourney(ReturnJourney returnJourney) {
        this.returnJourney = returnJourney;
    }

    public JourneyWithVia getJourneyWithVia() {
        return journeyWithVia;
    }

    public void setJourneyWithVia(JourneyWithVia journeyWithVia) {
        this.journeyWithVia = journeyWithVia;
    }
}
