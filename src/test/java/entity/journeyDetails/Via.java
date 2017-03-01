package entity.journeyDetails;

public class Via {
    private String buildingNumber;
    private String location;

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        if(buildingNumber==null)
            return location;
        else
            return buildingNumber+", "+location;
    }
}
