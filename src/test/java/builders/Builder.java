package builders;

import org.apache.commons.lang3.math.NumberUtils;
import pages.BasePage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static utils.Constants.*;
import static utils.Constants.Currency.POUND;

/**
 * <div>
 * Creating an abstract builder class which is the root of all builder classes.
 * This class cannot be instantiated and the user should either use concrete implementation of this class or extend it.
 * </div>
 */
public class Builder extends BasePage {


    public Builder() throws Exception {
    }

    /**
     * Builds todays date for booking a cab
     * @return
     */
    protected Date getTodaysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        return today;
    }

    protected Date getTomorrowsDate() {
        return getDaysAhead(1);
    }

    protected Date getDaysAhead(int amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, amount);
        Date tomorrow = calendar.getTime();
        return tomorrow;
    }

    protected Date getYesterdaysDate() {
        return getDaysAhead(-1);
    }

    protected boolean isValidDateFormat(String date) {
        boolean validDateFormat = false;
        try {
            Date sdf = new SimpleDateFormat(DATEFORMAT).parse(date);
            validDateFormat = true;
        }catch (Exception dateFormatException) {

        }
        return validDateFormat;
    }

    protected String getPriceWithLevy(String actualPrice) {
        logger.info(POUND);
        String revisedPrice = POUND+getPriceValueWithLevy(actualPrice);
        logger.info("Revised Price "+revisedPrice);
        return revisedPrice;
    }

    protected String getPriceWithoutLevy(String actualPrice) {
        logger.info(POUND);
        String revisedPrice = POUND+getPriceValueWithoutLevy(actualPrice);
        logger.info("Revised Price "+revisedPrice);
        return revisedPrice;
    }


    protected String getTotalPrice(String pickupPrice, String returnPrice) {
        double pickupPriceValue = getPriceValueWithoutLevy(pickupPrice);
        double returnPriceValue = getPriceValueWithoutLevy(returnPrice);
        double totalValue  = round(pickupPriceValue+returnPriceValue,2);
        return getPriceWithoutLevy(String.valueOf(totalValue));
    }

    protected String getTotalPrice(String pickupPrice) {
        double pickupPriceValue = getPriceValueWithoutLevy(pickupPrice);
        double totalValue  = round(pickupPriceValue,2);

        return getPriceWithoutLevy(String.valueOf(totalValue));
    }


    protected String getTotalPriceWithLevy(String pickupPrice, String returnPrice) {
        double pickupPriceValue = getPriceValueWithLevy(pickupPrice);
        double returnPriceValue = getPriceValueWithLevy(returnPrice);
        double totalValue  = round(pickupPriceValue+returnPriceValue,2);
        return getPriceWithLevy(String.valueOf(totalValue));
    }


    protected String getTotalPriceWithoutLevy(String pickupPrice, String returnPrice) {
        double pickupPriceValue = getPriceValueWithoutLevy(pickupPrice);
        double returnPriceValue = getPriceValueWithoutLevy(returnPrice);
        double totalValue  = round(pickupPriceValue+returnPriceValue,2);
        return getPriceWithoutLevy(String.valueOf(totalValue));
    }

    protected double getPriceValueWithLevy(String actualPrice) {
        String priceWithoutDollar = actualPrice.replace(POUND,"");
        double price = NumberUtils.toDouble(priceWithoutDollar);
        double revisedValue = price+((price*3)/100);
        double roundedValue = round(revisedValue,2);
        return roundedValue;
    }

    protected double getPriceValueWithoutLevy(String actualPrice) {
        String priceWithoutDollar = actualPrice.replace(POUND,"");
        double price = NumberUtils.toDouble(priceWithoutDollar);
        double revisedValue = price;
        double roundedValue = round(revisedValue,2);
        return roundedValue;
    }

}
