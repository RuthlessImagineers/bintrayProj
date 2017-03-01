package exceptions;

import org.openqa.selenium.NoSuchElementException;

/**
 * Created by krishnanand on 25/07/16.
 */
public class LocationException extends NoSuchElementException {


    public LocationException(String message, Throwable cause) {
        super(message,cause);
    }

    public LocationException(String message) {
        super(message);
    }
}
