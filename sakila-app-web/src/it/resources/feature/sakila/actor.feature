Feature: actor
# {
#   "actor_id": Int,
#   "first_name": String,
#   "last_name": String,
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/actors'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response == { actor_id: 1,  first_name: 'PENELOPE', last_name: 'GUINESS', last_update: '#notnull' }

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
    And match response == { actor_id: 2,  first_name: 'NICK', last_name: 'WAHLBERG', last_update: '#notnull' }
    And def actorId = response.actor_id

    Given path actorId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @create
  Scenario: create
    Given request { first_name: 'そろそろ', last_name: 'わざわざ' }
    When method post
    Then status 200
    And match response == { actor_id: '#number',  first_name: 'そろそろ', last_name: 'わざわざ', last_update: '#notnull' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response == { actor_id: 3,  first_name: 'ED', last_name: 'CHASE', last_update: '#notnull' }
    And def actorId = response.actor_id

    Given path actorId
    And request { actor_id: '#(actorId)', first_name: 'ころころ', last_name: 'ひそひそ' }
    When method put
    Then status 201
    And match response == { actor_id: '#(actorId)',  first_name: 'ころころ', last_name: 'ひそひそ', last_update: '#notnull' }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { actor_id: 1374, first_name: 'ころころ', last_name: 'ひそひそ' }
    When method put
    Then status 404
