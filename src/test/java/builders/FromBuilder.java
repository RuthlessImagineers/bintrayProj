package builders;

import entity.From;

import java.util.Date;

/**
 * <p>FromBuilder builds an object to be used with a pickup location, pickup date and pickup time</p>
 *
 */
public class FromBuilder extends Builder {

   private From from = pageStore.get(From.class);


    public FromBuilder() throws Exception {
        from.setPickupLocation("Elgin Rail Station");
        from.setPickupDate(getTodaysDate());
        from.setPickupHours("20");
        from.setPickupMinutes("15");
    }

    public FromBuilder withPickupLocation(String picupLocation) {
        from.setPickupLocation(picupLocation);
        return this;
    }

    public FromBuilder withPickupDate(Date pickupDate) {
        from.setPickupDate(pickupDate);
        return this;
    }

    public FromBuilder withPickupHour(String pickupHour) {
        from.setPickupHours(pickupHour);
        return this;
    }

    public FromBuilder withPickupMinutes(String pickupMinutes) {
        from.setPickupMinutes(pickupMinutes);
        return this;
    }

    public From build() {
        return from;

    }
}
