package entity;

/**
 * Created by krishnanand on 28/07/16.
 */
public class TripSummary {

    private String tripTotal;

    private String from;

    private String to;

    private String pickupTime;

    private String pickupDate;

    private String totalNoOfPassengers;

    private String totalLuggageCount;

    private String viaCount;

    private String cabProviderName;


    public String getTripTotal() {
        return tripTotal;
    }

    public void setTripTotal(String tripTotal) {
        this.tripTotal = tripTotal;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getTotalNoOfPassengers() {
        return totalNoOfPassengers;
    }

    public void setTotalNoOfPassengers(String totalNoOfPassengers) {
        this.totalNoOfPassengers = totalNoOfPassengers;
    }

    public String getTotalLuggageCount() {
        return totalLuggageCount;
    }

    public void setTotalLuggageCount(String totalLuggageCount) {
        this.totalLuggageCount = totalLuggageCount;
    }

    public String getViaCount() { return viaCount; }

    public void setViaCount(String viaCount) { this.viaCount = viaCount; }

    public String getCabProviderName() { return cabProviderName; }

    public void setCabProviderName(String cabProviderName) { this.cabProviderName = cabProviderName; }

}
