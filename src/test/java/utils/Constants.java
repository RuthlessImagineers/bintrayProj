package utils;

import java.io.File;
import java.util.Currency;
import java.util.Locale;

import static utils.Constants.Extenstions.*;
import static utils.Constants.FolderLocations.*;

public interface Constants {

    String SEPERATOR = File.separator;
    String DATEFORMAT = "MMMM/dd/YYYY";
    String MONTHFORMAT = "MMM";
    int MAXIMUM_PASSENGERS = 8;
    String USER_DIR = System.getProperty("user.dir");
    String BOOKING_REF_REGEX = "[0-9]{10}-[0-9]{4}";
    String BOOKING_REF_REGEX1 = "[0-9]{9}-[0-9]{4}";
    String DEFAULT_BOOKING = "singleJourney";

    interface FolderLocations {
        String TESTDATA = USER_DIR+SEPERATOR+"src"+SEPERATOR+"test"+SEPERATOR+"java"+SEPERATOR+"testdata"+SEPERATOR;//"/Users/krishnanand/Development/project_minicabit/android/minicabit-android/AndroidAutomation/src/test/java/testdata/";
        String CARDDATA = TESTDATA+"cardDetails"+SEPERATOR;
    }

    interface DataFileNames {
        String GUEST_USER_DETAILS = TESTDATA+"GuestUserDetails"+JSON;
        String JOURNEY_DETALS = TESTDATA+"JourneyDetails"+JSON;
        String REGISTRATION_DETAILS = TESTDATA+"RegistrationDetails"+JSON;
        String LOGIN_DETAILS = TESTDATA+"LoginDetails"+JSON;
        String VALID_CARD_DETAILS = CARDDATA+"ValidCardDetails"+JSON;
        String INVALID_CARD_DETAILS = CARDDATA+"InvalidCardDetails"+JSON;
        String LUGGAGE_DETAILS = TESTDATA+"LuggageDetails"+JSON;
    }

    interface DataFileStrings {
        String LOGIN_DETAILS = "LoginDetails";
        String REGISTRATION_DETAILS = "RegistrationDetails";
        String EXISTING_USER_DETAILS = "ExistingUserRegistrationDetails";
    }

    interface Extenstions {

       String JSON = ".json";
    }

    interface PopupMessages {
        String VERIFY_EMAIL = "An email containing a link to verify " +
                "your email address has been sent to your email account.";
        String NO_CASH = "You cannot select 'Cash to driver' as you have chosen a cab provider" +
                " that does not accept cash bookings.";
        String SIMILAR_LUGGAGE_SELECTION = "If your luggage item is not in the selection, " +
                "please select a similar sized item.";
        String VIA_BUILDING_NUMBER = "Please specify your location" +
                " to help your driver find you";
        String WARNING_CLEAR_TRIP_DETAILS = "Are you sure you want " +
                "to clear your trip details?";
        String WARNING_FAVOURITE_MESSAGE = "Are you sure you wish to remove";//"^Are you sure you wish to remove [\\w+] from your favourite locations?$";

        String ERROR_SERVER = "Oops! There's an unknown server error. Please can you close the app and open it again.";

        String REGISTER_EMAIL_EXISTS = "The bad news is we couldn't register you to minicabit. " +
                "The good news is that's because this email address has already been registered! " +
                "If you have forgotten your password, you can request it to be reset on the login page";

        String HIDDEN_CAB_OPERATORS = "Cab Operators' names are hidden so they can offer better quotes " +
                "here than if calling them directly. Full contact details of your selected provider will be presented " +
                "after booking.";
    }

    interface CommonData {

        String FLIGHT_NUMBER = "BA123";
        String CASH_ONLY = "Cash Only";
        String CARD_ONLY = "Card Only";
        String HOME_FAVOURITE = "Elgin Rail Station (IV30 1QP)";
        String WORK_FAVOURITE = "Dr Gray's Hospital - Elgin (IV30 1SN)";
        int LEVY_PRICE = 3;
    }


    interface CardTypes {
        String VISA_CREDIT = "VISA Credit";
        String VISA_DEBIT = "VISA Debit";
        String VISA_ELECTRON = "VISA Electron";
        String MASTERCARD_CREDIT = "MasterCard (Credit)";
        String MASTERCARD_DEBIT = "MasterCard (Debit)";
        String MAESTRO = "Maestro";
        String AMERICAN_EXPRESS = "American Express";
    }

    final class Currency {
        public static final String POUND = getCurrency(Locale.UK);

        private static String getCurrency(Locale locale) {
            String currency = java.util.Currency.getInstance(Locale.UK).getSymbol(Locale.UK);
            return currency;
        }
    }

    interface GroupNames {
       String LUGGAGE = "Luggage";
       String OUTDOOR_ITEMS = "Outdoor items";
       String CHILD_ITEMS = "Child items";
       String BOXES = "Boxes";
       String HOUSEHOLD_ITEMS  = "Household items";
       String FOLDED_WHEELCHAIR = "Folded wheelchair";
       String PETS = "Pets";

        interface Luggage {
            String SMALL_SUITCASE = "Small suitcase";
            String MEDIUM_SUITCASE = "Medium suitcase";
            String LARGE_SUITCASE = "Large Suitcase";
            String BACKPACK = "Backpack";
        }

        interface Outdoor {
            String TENT = "Tent";
            String CAMPING_BAG = "Camping bag";
            String PAIR_OF_SKIS = "Pair of skis";
            String SNOWBOARD = "Snowboard";
            String BIKE = "Bike";
            String BIKE_BOX = "Bike box";
            String GOLF_BAG = "Golf bag";
        }
    }

    interface PlaceHolders {
        String LOCATION_PLACEHOLDER = "Enter postcode, Venue or Place";
    }

    interface FavouriteAs {
        String HOME = "Home";
        String WORK = "Work";
    }

    interface Widgets {
        String TEXT_VIEW = "//android.widget.TextView";
    }

    interface MandatoryFieldsMessages {
        String MISSING_TITLE = "Please select your Title";
        String MISSING_FIRST_NAME = "Please enter your First Name";
        String MISSING_LAST_NAME = "Please enter your Last Name";
        String MISSING_EMAIL = "Email address does not exist";
        String MISSING_MOBILE_NUMBER = "Mobile format not recognised";
        String MISSING_PASSWORD = "Your password needs to contain a minimum of 6 characters, " +
                "including 1 number (0-9) or a special character. No password123's either!";
        String MISSING_CONFIRM_PASSWORD = "Please confirm your Password";
        String MISMATCH_PASSWORDS = "Your Password and Confirmed Password should match";
    }

}

