Feature: film_actor
# {
#   "actor_id": Int,
#   "film": {},
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/filmactors'

  @read
  Scenario: read(found)
    Given path 1, 1
    When method get
    Then status 200
    And match response contains { actor_id: 1}
    And match response contains { last_update: '#notnull' }
    And match response contains { film: '#object' }
    And match response.film contains { film_id: 1 }

  @read
  Scenario: read(not found)
    Given path 1374, 1374
    When method get
    Then status 404

  @ignore @delete
  Scenario: delete(found)
    Given path 2, 3
    When method get
    Then status 200
    And match response contains { actor_id: 2 }
    And match response contains { last_update: '#notnull' }
    And match response contains { film: '#object' }
    And match response.film contains { film_id: 3 }
    * def filmActorId = response.actor_id
    * def filmId = response.film.film_id

    Given path filmActorId, filmId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374, 1374
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def film_actor = { film: { film_id: 1374 } }
    And request film_actor
    When method post
    Then status 200
    And match response contains { actor_id: #number }
    And match response contains { last_update: '#notnull' }
    And match response contains { film: '#object' }
    And match response.film contains { film_id: 3 }

  @ignore @update
  Scenario: update(found)
    Given path 3, 17
    When method get
    Then status 200
    And match response contains { actor_id: 3 }
    And match response contains { last_update: '#notnull' }
    And match response contains { film: '#object' }
    And match response.film contains { film_id: 17 }
    * def film_actor = response

    Given path film_actor.actor_id, film_actor.film.film_id
    And request film_actor
    When method put
    Then status 201
    And match response contains { actor_id: 3 }
    And match response contains { last_update: '#notnull' }
    And match response contains { films: '#array' }
    And match response contains { film: '#object' }
    And match response.film contains { film_id: 17 }

  @update
  Scenario: update(not found)
    Given path 1374, 1374
    And request { actor_id: 1374, film: { film_id: 1374 } }
    When method put
    Then status 404
