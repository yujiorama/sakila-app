Feature: inventory

  Background:
    * url appUrl + '/inventories'
    * def schema = {}
    * set schema.inventory = read('schema/inventory.json')
    * set schema.film = read('schema/film.json')
    * set schema.store = read('schema/store.json')

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response == schema.inventory
    And match response.film == schema.film
    And match response.store == schema.store
    * match response contains { inventory_id: 1 }

  @read
  Scenario: read(not found)
    Given path 1374001
    When method get
    Then status 404

  @ignore @delete
  Scenario: delete(found)
    Given path 2
    When method get
    Then status 200
    And match response == schema.inventory
    And match response.film == schema.film
    And match response.store == schema.store
    * match response contains { inventory_id: 2 }
    * def inventoryId = response.inventory_id

    Given path inventoryId,
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374001
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def inventory = { film: { film_id: 1374001 }, store: { store_id: 1374001 } }
    And request inventory
    When method post
    Then status 200
    And match response == schema.inventory
    And match response.film == schema.film
    And match response.store == schema.store

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response == schema.inventory
    And match response.film == schema.film
    And match response.store == schema.store
    * match response contains { inventory_id: 3 }
    * def inventory = response

    Given path inventory.inventory_id
    And request inventory
    When method put
    Then status 201
    And match response == schema.inventory
    And match response.film == schema.film
    And match response.store == schema.store
    * match response contains { inventory_id: 3 }

  @update
  Scenario: update(not found)
    Given path 1374001
    And request { inventory_id: 1374001, film: { film_id: 1374001 }, store: { store_id: 1374001 } }
    When method put
    Then status 404
