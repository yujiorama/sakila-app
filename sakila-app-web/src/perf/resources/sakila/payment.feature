Feature: payment

  Background:
    * url appUrl + '/payments'
    * def schema = {}
    * set schema.payment = read('schema/payment.json')
    * set schema.customer = read('schema/customer.json')
    * set schema.staff = read('schema/staff.json')
    * set schema.rental = read('schema/rental.json')

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response == schema.payment
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.rental == schema.rental
    * match response contains { payment_id: 1 }

  @read
  Scenario: read(not found)
    Given path 1374001
    When method get
    Then status 404

  @delete
  Scenario: delete(found)
    Given path 2
    When method get
    Then status 200
    And match response == schema.payment
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.rental == schema.rental
    * match response contains { payment_id: 2 }
    * def paymentId = response.payment_id

    Given path paymentId,
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374001
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def payment = { customer: { customer_id: 1374001 }, staff: { staff_id: 1374001 }, rental: { rental_id: 1374001 } }
    And request payment
    When method post
    Then status 200
    And match response == schema.payment
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.rental == schema.rental

  @ignore @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response == schema.payment
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.rental == schema.rental
    And match response contains { payment_id: 3 }
    * def payment = response
    * set payment.amount = 13.74

    Given path payment.payment_id
    And request payment
    When method put
    Then status 201
    And match response == schema.payment
    And match response.customer == schema.customer
    And match response.staff == schema.staff
    And match response.rental == schema.rental
    And match response contains { payment_id: 3 }
    And match response contains { amount: 13.74 }

  @update
  Scenario: update(not found)
    Given path 1374001
    And request { payment_id: 1374001, customer: { customer_id: 1374001 }, staff: { staff_id: 1374001 }, rental: { rental_id: 1374001 } }
    When method put
    Then status 404
