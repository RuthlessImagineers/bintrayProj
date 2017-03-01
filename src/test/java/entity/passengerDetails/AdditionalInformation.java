package entity.passengerDetails;

/**
 * Created by krishnanand on 26/07/16.
 */
public class AdditionalInformation {

    private String luggageDetails;
    private String flightNo;
    private Boolean isBusinessTrip;

    public String getLuggageDetails() {
        return luggageDetails;
    }

    public void setLuggageDetails(String luggageDetails) {
        this.luggageDetails = luggageDetails;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Boolean getBusinessTrip() {
        return isBusinessTrip;
    }

    public void setBusinessTrip(Boolean businessTrip) {
        isBusinessTrip = businessTrip;
    }
}
