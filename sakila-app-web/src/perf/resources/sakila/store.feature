Feature: store

  Background:
    * url appUrl + '/stores'
    * def schema = {}
    * set schema.store = read('schema/store.json')
    * set schema.address = read('schema/address.json')
    * set schema.staff = read('schema/staff.json')

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response == schema.store
    And match response.address == schema.address
    And match response.manager_staff == schema.staff
    And match response contains { store_id: 1 }

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
    And match response == schema.store
    And match response.address == schema.address
    And match response.manager_staff == schema.staff
    And match response contains { store_id: 2 }
    * def storeId = response.store_id

    Given path storeId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374001
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def store = { address: { address_id: 1374001 }, manager_staff: { staff_id: 1374001 } }
    And request store
    When method post
    Then status 200
    And match response == schema.store
    And match response.address == schema.address
    And match response.manager_staff == schema.staff

  @ignore @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response == schema.store
    And match response.address == schema.address
    And match response.manager_staff == schema.staff
    And match response contains { store_id: 3 }
    * def store = response

    Given path store.store_id
    And request store
    When method put
    Then status 201
    And match response == schema.store
    And match response.address == schema.address
    And match response.manager_staff == schema.staff
    And match response contains { store_id: 3 }

  @update
  Scenario: update(not found)
    Given path 1374001
    And request { store_id: 1374001, address: { address_id: 1374001 }, manager_staff: { staff_id: 1374001 } }
    When method put
    Then status 404
