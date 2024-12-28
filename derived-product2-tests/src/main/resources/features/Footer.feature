Feature: Validate footer hyperlinks on DP2 home page

  Scenario: Verify footer links and check for duplicates
    Given I navigate to the DP2 home page
    When I scroll down to the footer
    Then I fetch all footer hyperlinks into a CSV file
    And I validate for any duplicate hyperlinks in the footer
