Feature: film
# {
#   "film_id": Int,
#   "title": String,
#   "rental_duration": Int,
#   "rental_rate": BigDecimal,
#   "replacement_cost": BigDecimal,
#   "rating": String,
#   "description": String?,
#   "release_year": Int?,
#   "length": Int?,
#   "last_update": LocalDateTime
#   "language": {},
#   "original_language": {}?,
#   "special_features": []String,
# }
  Background:
    * url appUrl + '/films'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response contains { film_id: 1}
    And match response contains { title: '#string' }
    And match response contains { rental_duration: '#number' }
    And match response contains { rental_rate: '#number' }
    And match response contains { replacement_cost: '#number' }
    And match response contains { rating: '#string' }
    And match response contains { description: '##string' }
    And match response contains { release_year: '##number' }
    And match response contains { length: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { language: '#object' }
    And match response.language contains { language_id: #number }
    And match response contains { original_language: '##object' }
    And match response contains { special_features: '#array' }

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
    And match response contains { film_id: 2 }
    And match response contains { title: '#string' }
    And match response contains { rental_duration: '#number' }
    And match response contains { rental_rate: '#number' }
    And match response contains { replacement_cost: '#number' }
    And match response contains { rating: '#string' }
    And match response contains { description: '##string' }
    And match response contains { release_year: '##number' }
    And match response contains { length: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { language: '#object' }
    And match response.language contains { language_id: #number }
    And match response contains { original_language: '##object' }
    And match response contains { special_features: '#array' }
    And def filmId = response.film_id

    Given path filmId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def film = { title: 'そろそろ', rental_duration: 7 }
    And request film
    When method post
    Then status 200
    And match response contains { film_id: #number }
    And match response contains { title: 'そろそろ' }
    And match response contains { rental_duration: 7 }
    And match response contains { rental_rate: '#number' }
    And match response contains { replacement_cost: '#number' }
    And match response contains { rating: '#string' }
    And match response contains { description: '##string' }
    And match response contains { release_year: '##number' }
    And match response contains { length: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { language: '#object' }
    And match response.language contains { language_id: #number }
    And match response contains { original_language: '##object' }
    And match response contains { special_features: '#array' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response contains { film_id: 3 }
    And match response contains { title: '#string' }
    And match response contains { rental_duration: '#number' }
    And match response contains { rental_rate: '#number' }
    And match response contains { replacement_cost: '#number' }
    And match response contains { rating: '#string' }
    And match response contains { description: '##string' }
    And match response contains { release_year: '##number' }
    And match response contains { length: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { language: '#object' }
    And match response.language contains { language_id: #number }
    And match response contains { original_language: '##object' }
    And match response contains { special_features: '#array' }
    And def film = response
    * set film.title = 'ころころ'
    * set film.rental_duration = 14
    * set film.rental_rate = 2.5

    Given path film.film_id
    And request film
    When method put
    Then status 201
    And match response contains { film_id: 3 }
    And match response contains { title: 'ころころ' }
    And match response contains { rental_duration: 14 }
    And match response contains { rental_rate: 2.5 }
    And match response contains { replacement_cost: '#number' }
    And match response contains { rating: '#string' }
    And match response contains { description: '##string' }
    And match response contains { release_year: '##number' }
    And match response contains { length: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { language: '#object' }
    And match response.language contains { language_id: #number }
    And match response contains { original_language: '##object' }
    And match response contains { special_features: '#array' }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { film_id: 1374, title: 'ころころ', rental_duration: 14 }
    When method put
    Then status 404
