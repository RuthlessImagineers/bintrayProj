package enums;

/**
 * Created by krishnanand on 18/08/16.
 */
public enum HomeMenuOptions {
    LOGIN,
    REGISTER,
    MY_ACCOUNT,
    BOOK_A_TRIP,
    TELL_A_FRIEND,
    CONTACT_US,
    OTHER;

   public enum ContactUs {
       LIVE_CHAT,
       EMAIL_US
   }

    public enum OTHER {
        ABOUT_US,
        HOW_IT_WORKS,
        FAQs,
        TERMS_AND_CONDITIONS,
        RATE_THIS_APP,
        VERSION
    }

    public enum MyAccount {
        MY_BOOKINGS,
        PASSENGER_INFO,
        BILLING,
        ACCOUNT
    }

}
