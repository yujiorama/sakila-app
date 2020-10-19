Feature: rental

  Background:
    * url appUrl + '/rentals'
    * def schema = {}
    * set schema.rental = read('schema/sakila/rental.json')
    * set schema.customer = read('schema/sakila/customer.json')
    * set schema.staff = read('schema/sakila/staff.json')
    * set schema.inventory = read('schema/sakila/inventory.json')

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response == schema.rental
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.inventory == schema.inventory
    And match response contains { rental_id: 1 }

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
    And match response == schema.rental
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.inventory == schema.inventory
    And match response contains { rental_id: 2 }
    * def rentalId = response.rental_id

    Given path rentalId,
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374001
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def rental = { customer: { customer_id: 1374001 }, staff: { staff_id: 1374001 }, inventory: { inventory_id: 1374001 } }
    And request rental
    When method post
    Then status 200
    And match response == schema.rental
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.inventory == schema.inventory

  @ignore @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response == schema.rental
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.inventory == schema.inventory
    And match response contains { rental_id: 3 }
    * def rental = response
    * set rental.return_date = '2020-01-02T03:04:05.006+09:00'

    Given path rental.rental_id
    And request rental
    When method put
    Then status 201
    And match response == schema.rental
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.inventory == schema.inventory
    And match response contains { rental_id: 3 }
    And match response contains { return_date: '2020-01-02T03:04:05.006+09:00' }

  @update
  Scenario: update(not found)
    Given path 1374001
    And request { rental_id: 1374001, customer: { customer_id: 1374001 }, staff: { staff_id: 1374001 }, rental: { rental_id: 1374001 } }
    When method put
    Then status 404
