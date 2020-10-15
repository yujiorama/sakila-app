Feature: city
# {
#   "city_id": Int,
#   "city": String,
#   "country": {},
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/cities'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response contains { city_id: 1}
    And match response contains { city: '#string' }
    And match response contains { last_update: '#notnull' }
    And match response contains { country: '#object' }
    And match response.country contains { country_id: #number, country: '#string', last_update: '#notnull' }

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
    And match response contains { city_id: 2 }
    And match response contains { city: '#string' }
    And match response contains { last_update: '#notnull' }
    And match response contains { country: '#object' }
    And match response.country contains { country_id: #number, country: '#string', last_update: '#notnull' }
    And def cityId = response.city_id

    Given path cityId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def city = { city: 'そろそろ' }
    And request city
    When method post
    Then status 200
    And match response contains { city_id: #number }
    And match response contains { city: 'そろそろ' }
    And match response contains { last_update: '#notnull' }
    And match response contains { country: '#object' }
    And match response.country contains { country_id: #number, country: '#string', last_update: '#notnull' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response contains { city_id: 3 }
    And match response contains { city: '#string' }
    And match response contains { last_update: '#notnull' }
    And match response contains { country: '#object' }
    And match response.country contains { country_id: #number, country: '#string', last_update: '#notnull' }
    And def city = response
    * set city.city = 'ころころ'

    Given path city.city_id
    And request city
    When method put
    Then status 201
    And match response contains { city_id: 3 }
    And match response contains { city: 'ころころ' }
    And match response contains { last_update: '#notnull' }
    And match response contains { country: '#object' }
    And match response.country contains { country_id: #number, country: '#string', last_update: '#notnull' }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { city_id: 1374, city: 'ころころ' }
    When method put
    Then status 404
