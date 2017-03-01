package entity.passengerDetails;

import enums.Title;

/**
 * Created by krishnanand on 26/07/16.
 */
public class YourDetails {

    private Title title;
    private String firstName;
    private String lastName;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
