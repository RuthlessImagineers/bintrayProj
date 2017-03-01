package entity;


public class TimePicker {

    private String hours;
    private String minutes;


    public TimePicker(String time) {
        String[] hrMn = time.split(":");
        setHours(hrMn[0]);
        setMinutes(hrMn[1]);
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}
