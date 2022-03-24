# Created by david at 24.03.2022
Feature: Bestellungen
  # Enter feature description here

  Scenario: Get Bestellung for user
    Given path '/menue/bestellung'
    And  params { name: 'spabo' }
    When method GET
    Then status 200

  Scenario: Get Bestellung Stats for user
    Given path '/menue/bestellung/stats'
    And  params { name: 'spabo' }
    When method GET
    Then status 200

  Scenario: Get Categories
    Given path '/menue/bestellung/stats'
    When method GET
    Then status 200
