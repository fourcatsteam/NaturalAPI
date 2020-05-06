# new feature
# Tags: optional

Feature: test

  Scenario: A scenario
    Given no character with name "Ayran" exists in the current project
    When I try to create a character with name "Ayran"
    Then the character with name "Ayran" is created
    And I can lookup the character with name "Ayran" in the current project