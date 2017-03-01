package builders;

import entity.Booking;

public class BookingBuilder extends Builder{

    Booking booking = new Booking();

    public BookingBuilder() throws Exception {
    }

    public BookingBuilder withBookedTitle(String bookedTitle) {
        booking.setBookedTitle(bookedTitle);
        return this;
    }

    public BookingBuilder withBookedPrice(String bookedPrice) {
        booking.setBookedPrice(bookedPrice);
        return this;
    }

    public BookingBuilder withBookedFrom(String bookedFrom) {
        booking.setBookedFrom(bookedFrom);
        return this;
    }

    public BookingBuilder withBookedTo(String bookedTo)  {
        booking.setBookedTo(bookedTo);
        return this;
    }

    public BookingBuilder withBookedTripBase(String bookedTripBase) {
        booking.setBookedTripBase(bookedTripBase);
        return this;
    }

    public BookingBuilder withBookedEmission(String bookedEmission) {
        booking.setBookedEmission(bookedEmission);
        return this;
    }

    public BookingBuilder withBookedDate(String bookedDate) {
        booking.setBookedDate(bookedDate);
        return this;
    }

    public Booking build() {
        return booking;
    }

}
