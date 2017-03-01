@MyBookings
Feature: My Bookings
  As a registered user I should be able to view my bookings history

  @FutureBookings @regression @e2e
  Scenario Outline: As a registered user my bookings should be remembered
    Given On Homepage I login as registered user
    And On Homepage I enter trip details from <tripData> for trip type <tripDetails> and with luggage <additionalLuggage>
    And On Homepage I choose a cab of payment type <paymentType>
    And On TripSummary page I select payment method
    And On Payment page I select payment type as <paymentType> and enter paymentDetails <paymentDetails> of <cardType>
    And On TripSummary page I enter flight details and confirm booking
    Then On Trip confirmation page I should receive a booking confirmation reference Id
    And On Homepage I navigate to my bookings
    Then On MyBookings page my latest booking should be present

    Examples:
      | tripData     | tripDetails    | additionalLuggage |paymentType  | paymentDetails |    cardType   |
      | SplitBooking | returnjourney  |    Luggage        | cash        |      NA        |       NA      |
      |JourneyDetails| singlejourney  |    Luggage        | card        |ValidCardDetails|AmericanExpress|
      |JourneyDetails| journeywithvia |    Luggage        |existingcard |      NA        |       NA      |