Feature: category
# {
#   "category_id": Int,
#   "name": String,
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/categories'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response contains { category_id: 1}
    And match response contains { name: '#string' }
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
    And match response contains { category_id: 2 }
    And match response contains { name: '#string' }
    And match response contains { last_update: '#notnull' }
    And def categoryId = response.category_id

    Given path categoryId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @create
  Scenario: create
    Given def category = { name: 'そろそろ' }
    And request category
    When method post
    Then status 200
    And match response contains { category_id: #number }
    And match response contains { name: 'そろそろ' }
    And match response contains { last_update: '#notnull' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response contains { category_id: 3 }
    And match response contains { name: '#string' }
    And match response contains { last_update: '#notnull' }
    And def category = response
    * set category.name = 'ころころ'

    Given path category.category_id
    And request category
    When method put
    Then status 201
    And match response contains { category_id: 3 }
    And match response contains { name: 'ころころ' }
    And match response contains { last_update: '#notnull' }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { category_id: 1374, name: 'ころころ' }
    When method put
    Then status 404
