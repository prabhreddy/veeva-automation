Feature: Core Product Shopping

  Background:
    Given I navigate to the Core Product home page


  Scenario: Extract and store jacket details
    Given I navigate to the shop menu for men's jackets
    When I extract jacket details from all pages
    Then I store jacket details in a text file and attach it to the report


  Scenario: Count video feeds in New & Features section
    Given I navigate to the New & Features section from the menu
    When I count total video feeds and video feeds posted within 3 days
    Then I log the video feed counts in the report

