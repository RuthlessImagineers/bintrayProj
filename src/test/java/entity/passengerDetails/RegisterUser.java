package entity.passengerDetails;

/**
 * Created by krishnanand on 26/07/16.
 */
public class RegisterUser {

    private boolean saveDetails;
    private String password;
    private String confirmPassword;

    public boolean isSaveDetails() {
        return saveDetails;
    }

    public void setSaveDetails(boolean saveDetails) {
        this.saveDetails = saveDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
