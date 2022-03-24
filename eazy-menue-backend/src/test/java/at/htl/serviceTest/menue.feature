# Created by david at 24.03.2022
Feature: # Enter feature name here
  # Enter feature description here

  Scenario: Get Meunes
    Given path '/menue/menues'
    When method GET
    Then status 200

  Scenario: Add Menue
    Given path '/menue/menues'
    And request
    """
    [
      {

      }
    ]
    """
    When method GET
    Then status 200


