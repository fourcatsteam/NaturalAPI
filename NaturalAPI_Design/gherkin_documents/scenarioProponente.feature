Feature: Account Holder withdraws cash
  As a Account Holder

  Scenario: Account has sufficient funds
    Given the account balance is "$100"
    And the machine contains enough money
    And we introduce a "Visa" card
    And the card is valid
    When the Account Holder requests "$100"
    Then the ATM should dispense "$100"
    And the account balance should be "$0"
    And the card should be returned

  Scenario: Account has sufficient funds
    Given the account balance is "$100"
    And the machine contains enough money
    And we introduce a "AMEX" card
    And the card is valid
    When the Account Holder requests "$50"
    Then the ATM should dispense "$50"
    And the account balance should be "$50"
    And the card should be returned

  Scenario: Account has sufficient funds
    Given the account balance is "$100"
    And the machine contains enough money
    And we introduce a "Mastercard" card
    And the card is valid
    When the Account Holder requests "$20"
    Then the ATM should dispense "$20"
    And the account balance should be "$80"
    And the card should be returned

  Scenario: Account has insufficient funds
    Given the account balance is "$10"
    And we introduce a card
    And the card is valid
    And the machine contains enough money
    When the Account Holder requests "$20"
    Then the ATM should not dispense any money
    And the ATM should say there are insufficient funds

  Scenario: Card has been disabled
    Given the card is disabled
    And we introduce the card
    When the Account Holder requests "$10"
    Then the ATM should retain the card
    And the ATM should say the card has been retained