Feature: Validate Tickets Section in Derived Product 1
  Scenario: Validate slides on Derived Product 1 homepage
    Given I navigate to the Derived Product 1 homepage
    Then I count the total number of slides
    And I validate the title of each slide with the expected data
    And I validate the duration of each slide with the expected duration