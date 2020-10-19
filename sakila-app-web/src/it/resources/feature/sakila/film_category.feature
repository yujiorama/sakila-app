Feature: film_category
# {
#   "film_id": Int,
#   "category": {},
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/filmcategories'

  @read
  Scenario: read(found)
    Given path 1, 6
    When method get
    Then status 200
    And match response contains { film_id: 1}
    And match response contains { last_update: '#notnull' }
    And match response contains { category: '#object' }
    And match response.category contains { category_id: 6 }

  @read
  Scenario: read(not found)
    Given path 1374, 1374
    When method get
    Then status 404

  @ignore @delete
  Scenario: delete(found)
    Given path 2, 11
    When method get
    Then status 200
    And match response contains { film_id: 2 }
    And match response contains { last_update: '#notnull' }
    And match response contains { category: '#object' }
    And match response.category contains { category_id: 11 }
    * def filmId = response.film_id
    * def categoryId = response.film.category_id

    Given path filmId, categoryId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374, 1374
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def film_category = { category: { category_id: 1374 } }
    And request film_category
    When method post
    Then status 200
    And match response contains { film_id: #number }
    And match response contains { last_update: '#notnull' }
    And match response contains { category: '#object' }
    And match response.category contains { category_id: 3 }

  @ignore @update
  Scenario: update(found)
    Given path 3, 6
    When method get
    Then status 200
    And match response contains { film_id: 3 }
    And match response contains { last_update: '#notnull' }
    And match response contains { category: '#object' }
    And match response.category contains { category_id: 6 }
    * def film_category = response

    Given path film_category.film_id, film_category.category.category_id
    And request film_category
    When method put
    Then status 201
    And match response contains { film_id: 3 }
    And match response contains { last_update: '#notnull' }
    And match response contains { films: '#array' }
    And match response contains { category: '#object' }
    And match response.category contains { category_id: 17 }

  @update
  Scenario: update(not found)
    Given path 1374, 1374
    And request { film_id: 1374, category: { category_id: 1374 } }
    When method put
    Then status 404
