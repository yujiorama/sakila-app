Feature: actor test

  Background:
    * url appUrl + '/actors'

  @read
  Scenario: read
    Given path 1
    When method get
    Then status 200
    And match response == { actor_id: 1,  first_name: 'PENELOPE', last_name: 'GUINESS', last_update: '#notnull' }
