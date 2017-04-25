@Booking
Feature: User Booking
  As a user I should be able to book a cab from A to B.

  @smoke @singlejourney @journeywithvia @e2e @regression @check
  Scenario Outline: Make a booking for a cab with luggage
    Given On Homepage I enter trip details for trip type <tripDetails> and with luggage <additionalLuggage>
    And On Homepage I choose a cab of payment type <paymentType>
    And On PassengerDetails page I enter passenger login details <loginDetails>
    And On TripSummary page I select payment method
    And On Payment page I select payment type as <paymentType> and enter paymentDetails <paymentDetails> of <cardType>
    And On TripSummary page I enter flight details and confirm booking
    Then On Trip confirmation page I should receive a booking confirmation reference Id

    Examples:
      | tripDetails    |additionalLuggage              |loginDetails  |paymentType  | paymentDetails |    cardType   |
      | singlejourney  |    Luggage                    | LoginDetails | cash        |      NA        |       NA      |
#      | singlejourney  |    Luggage                    | LoginDetails | card        |ValidCardDetails|AmericanExpress|
#      | singlejourney  |    Luggage                    | LoginDetails |existingcard |      NA        |       NA      |
#      | journeywithvia |    Luggage                    | LoginDetails | cash        |      NA        |       NA      |
#      | journeywithvia |    Luggage                    | LoginDetails | card        |ValidCardDetails|AmericanExpress|
    | journeywithvia |    Luggage                    | LoginDetails |existingcard |      NA        |       NA      |


    @smoke @returnjourney @e2e @split-booking @regression
    Scenario Outline:  Booking a cab with return journey
      Given On Homepage I enter trip details from <tripData> for trip type <tripDetails> and with luggage <additionalLuggage>
      And On Homepage I choose a cab of payment type <paymentType>
      And On PassengerDetails page I enter passenger login details <loginDetails>
      And On TripSummary page I select payment method
      And On Payment page I select payment type as <paymentType> and enter paymentDetails <paymentDetails> of <cardType>
      And On TripSummary page I enter flight details and confirm booking
      Then On Trip confirmation page I should receive a booking confirmation reference Id

      Examples:
       | tripData     | tripDetails    | additionalLuggage |loginDetails  |paymentType  | paymentDetails |    cardType   |
       | SplitBooking | returnjourney  |    Luggage        | LoginDetails | cash        |      NA        |       NA      |
       | SplitBooking | returnjourney  |    Luggage        | LoginDetails | card        |ValidCardDetails|AmericanExpress|
       | SplitBooking | returnjourney  |    Luggage        | LoginDetails | existingcard|        NA      |       NA      |


      @loginAndBook @e2e @regression
      Scenario Outline: Booking a cab after logging in
        Given On Homepage I login as registered user
        And On Homepage I enter trip details from <tripData> for trip type <tripDetails> and with luggage <additionalLuggage>
        And On Homepage I choose a cab of payment type <paymentType>
        And On TripSummary page I select payment method
        And On Payment page I select payment type as <paymentType> and enter paymentDetails <paymentDetails> of <cardType>
        And On TripSummary page I enter flight details and confirm booking
        Then On Trip confirmation page I should receive a booking confirmation reference Id

        Examples:
          | tripData     | tripDetails    | additionalLuggage |paymentType  | paymentDetails |    cardType   |
          | SplitBooking | returnjourney  |    Luggage        | cash        |      NA        |       NA      |
          | SplitBooking | returnjourney  |    Luggage        | card        |ValidCardDetails|AmericanExpress|
          | SplitBooking | returnjourney  |    Luggage        | existingcard|        NA      |       NA      |


