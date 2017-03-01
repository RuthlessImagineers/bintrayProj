package entity;

/**
 * Created by krishnanand on 25/08/16.
 */
public class WarningPopups {

    private boolean isTitleWarning;
    private boolean isFirstNameWarning;
    private boolean isLastNameWarning;
    private boolean isEmailWarning;
    private boolean isMobileNumberWarning;
    private boolean isPasswordWarning;
    private boolean isConfirmPasswordWarning;


    public boolean isTitleWarning() {
        return isTitleWarning;
    }

    public void setTitleWarning(boolean titleWarning) {
        isTitleWarning = titleWarning;
    }

    public boolean isFirstNameWarning() {
        return isFirstNameWarning;
    }

    public void setFirstNameWarning(boolean firstNameWarning) {
        isFirstNameWarning = firstNameWarning;
    }

    public boolean isLastNameWarning() {
        return isLastNameWarning;
    }

    public void setLastNameWarning(boolean lastNameWarning) {
        isLastNameWarning = lastNameWarning;
    }

    public boolean isEmailWarning() {
        return isEmailWarning;
    }

    public void setEmailWarning(boolean emailWarning) {
        isEmailWarning = emailWarning;
    }

    public boolean isMobileNumberWarning() {
        return isMobileNumberWarning;
    }

    public void setMobileNumberWarning(boolean mobileNumberWarning) {
        isMobileNumberWarning = mobileNumberWarning;
    }

    public boolean isPasswordWarning() {
        return isPasswordWarning;
    }

    public void setPasswordWarning(boolean passwordWarning) {
        isPasswordWarning = passwordWarning;
    }

    public boolean isConfirmPasswordWarning() {
        return isConfirmPasswordWarning;
    }

    public void setConfirmPasswordWarning(boolean confirmPasswordWarning) {
        isConfirmPasswordWarning = confirmPasswordWarning;
    }
}
