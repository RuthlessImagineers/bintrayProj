@UUI
Feature: Unit User Interface tests
  As a user I should be able to use all handy features.

  @clearTripDetails
  Scenario: Clear trip details on homepage
    Given On Homepage I enter trip details
    And On Homepage I clear the trip details
    And On Hompage the system seeks my approval for clearing details
    When On Homepage I reject clearing trip details
    Then On Homepage my trip details should be intact
    And On Homepage I clear the trip details again
    When On Homepage I accept clearing trip details
    Then On Homepage my trip details should get cleared


  @favouriteLocations
  Scenario: Saving user favourite locations
    Given On Homepage I login as registered user
    And On Homepage I navigate to location page
    And On Location page I should be able to remove any existing favourites
    When On Location page I add my home and work favourites
    Then On Location page my favourite locations should be added



