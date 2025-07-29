@scroll @apidemos
Feature: Scroll in ApiDemos app

  Scenario: Scroll to 'Lists' in Views
    Given the app is launched
    When the user taps on "Views"
    And the user scrolls down
    Then the user should see "Lists"
