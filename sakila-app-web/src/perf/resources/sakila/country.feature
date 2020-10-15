Feature: country
# {
#   "country_id": Int,
#   "country": String,
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/countries'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response contains { country_id: 1}
    And match response contains { country: '#string' }
    And match response contains { last_update: '#notnull' }

  @read
  Scenario: read(not found)
    Given path 1374
    When method get
    Then status 404

  @ignore @delete
  Scenario: delete(found)
    Given path 2
    When method get
    Then status 200
    And match response contains { country_id: 2 }
    And match response contains { country: '#string' }
    And match response contains { last_update: '#notnull' }
    And def countryId = response.country_id

    Given path countryId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @create
  Scenario: create
    Given def country = { country: 'そろそろ' }
    And request country
    When method post
    Then status 200
    And match response contains { country_id: #number }
    And match response contains { country: 'そろそろ' }
    And match response contains { last_update: '#notnull' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response contains { country_id: 3 }
    And match response contains { country: '#string' }
    And match response contains { last_update: '#notnull' }
    And def country = response
    * set country.country = 'ころころ'

    Given path country.country_id
    And request country
    When method put
    Then status 201
    And match response contains { country_id: 3 }
    And match response contains { country: 'ころころ' }
    And match response contains { last_update: '#notnull' }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { country_id: 1374, country: 'ころころ' }
    When method put
    Then status 404
