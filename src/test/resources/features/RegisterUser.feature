@RegisterUser
Feature: As a user of Minicabit, I should be able to register for ease of booking

  @registerNewUser
  Scenario: Register a new user
    Given On Homepage I choose to go to registration page
    When On PassengerDetails page I create a new passenger with details "RegistrationDetails"
    Then On PassengerDetails page I should receive a successful message

  @registerExistingUser
  Scenario: Register same user
    Given On Homepage I choose to go to registration page
    When On PassengerDetails page I create a new passenger with existing details
    Then On PassengerDetails page I should receive a warning of user registration

  @mandatoryFields
  Scenario: As a new user I should not be able to register unless I fill all mandatory details
    Given From Homepage I navigate to passenger details page to create a new account
    When  On PassengerDetails page I try to register a user without entering details
    Then On PassengerDetails page I should be receive a suggestion of missing fields

  @mandatoryFieldOneByOne  @check
  Scenario Outline: As a new user I should not be able to register missing one of the mandatory details
    Given From Homepage I navigate to passenger details page to create a new account
    When  On PassengerDetails page I try to register a user without entering <mandatoryField> detail
    Then On PassengerDetails page I should be receive a suggestion of missing field <mandatoryField>

    Examples:
      | mandatoryField   |
      | title            |
      | first_name       |
#      | last_name        |
#      | email            |
#      | mobile_number    |
#      | password         |
#      | confirm_password |
