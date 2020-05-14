Feature: create pile
As a comic creator
I want to create a pile
so that I can group some panels together
without committing to stricter groupings like page, book etc

Scenario: completed
    Given I try to create a project with id "project1"
    And the project with id "project1" is created
    And no pile with name "pile1" exists in the current project
    When I try to create a pile with name "pile1"
    Then the pile with name "pile1" is created
    And I can lookup the pile with name "pile1" in the current project

Scenario: abandoned - pile already exists
    Given I try to create a project with id "project1"
    And the project with id "project1" is created
    And I try to create a pile with name "pile1"
    And a pile with name "pile1" exists in the current project
    When I try to create a pile with name "pile1"
    Then the pile with name "pile1" is not created
    And I am told that a pile with name "pile1" already exists