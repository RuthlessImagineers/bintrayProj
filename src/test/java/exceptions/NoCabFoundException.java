package exceptions;

/**
 * Created by krishnanand on 04/08/16.
 */
public class NoCabFoundException extends Exception {

    public NoCabFoundException() {
        super("Either the search yielded no cabs or there are no cabs for specific payment mode or there is an internal server error");
    }

    public NoCabFoundException(String message) {
        super(message);
    }


}
