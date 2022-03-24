Feature: The get statistics use case

  Background:
    * url baseUrl

  Scenario: Get Meunes
    Given path '/menue/oeffnungszeiten'
    When method GET
    Then status 200

  Scenario: Get Meunes
    Given path '/menue/oeffnungszeiten'
    And params { id: '1000' }
    When method GET
    Then status 200
