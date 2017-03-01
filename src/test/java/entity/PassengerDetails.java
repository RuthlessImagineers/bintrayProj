package entity;

import entity.passengerDetails.*;
import enums.UserType;

public class PassengerDetails {

       private SignIn signIn;

       private CreateAccount createAccount;

    public CreateAccount getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(CreateAccount createAccount) {
        this.createAccount = createAccount;
    }

    public SignIn getSignIn() {
        return signIn;
    }

    public void setSignIn(SignIn signIn) {
        this.signIn = signIn;
    }
}
