As a: Customer
Feature: Bank withdraw
  Scenario: Withdraw cash
    Given the user is in front of an ATM
    When the user inserts the card in the ATM
    And the user enters the pin of the card
    And the user enters the amount of cash he wants to withdraw
    Then the ATM should check the pin
    And the ATM should give the user the cash

  Scenario: Expired card
    Given the user is in front of an ATM
    When the user inserts the card in the ATM
    And the user enters the pin of the card
    And the user enters the amount of cash he wants to withdraw
    Then the ATM should check the validity of the card
    And the ATM should display an error message