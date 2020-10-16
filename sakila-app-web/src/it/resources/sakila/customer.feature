Feature: customer
# {
#   "customer_id": Int,
#   "store": {},
#   "address": {},
#   "first_name": String,
#   "last_name": String,
#   "activebool": Boolean,
#   "create_date": LocalDateTime,
#   "email": String?,
#   "active": Int?,
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/customers'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response contains { customer_id: 1}
    And match response contains { first_name: '#string' }
    And match response contains { last_name: '#string' }
    And match response contains { activebool: '#boolean' }
    And match response contains { create_date: '#notnull' }
    And match response contains { email: '##string' }
    And match response contains { active: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { store: '#object' }
    And match response.store contains { store_id: #number }
    And match response contains { address: '#object' }
    And match response.address contains { address_id: #number }

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
    And match response contains { customer_id: 2 }
    And match response contains { first_name: '#string' }
    And match response contains { last_name: '#string' }
    And match response contains { activebool: '#boolean' }
    And match response contains { create_date: '#notnull' }
    And match response contains { email: '##string' }
    And match response contains { active: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { store: '#object' }
    And match response.store contains { store_id: #number }
    And match response contains { address: '#object' }
    And match response.address contains { address_id: #number }
    And def customerId = response.customer_id

    Given path customerId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def customer = { first_name: 'そろそろ', last_name: 'わざわざ', activebool: true }
    And request customer
    When method post
    Then status 200
    And match response contains { customer_id: #number }
    And match response contains { first_name: 'そろそろ' }
    And match response contains { last_name: 'わざわざ' }
    And match response contains { last_update: '#notnull' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response contains { customer_id: 3 }
    And match response contains { first_name: '#string' }
    And match response contains { last_name: '#string' }
    And match response contains { activebool: '#boolean' }
    And match response contains { create_date: '#notnull' }
    And match response contains { email: '##string' }
    And match response contains { active: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { store: '#object' }
    And match response.store contains { store_id: #number }
    And match response contains { address: '#object' }
    And match response.address contains { address_id: #number }
    And def customer = response
    * set customer.first_name = 'ころころ'
    * set customer.last_name = 'ひそひそ'

    Given path customer.customer_id
    And request customer
    When method put
    Then status 201
    And match response contains { customer_id: 3 }
    And match response contains { first_name: 'ころころ' }
    And match response contains { last_name: 'ひそひそ' }
    And match response contains { activebool: '#boolean' }
    And match response contains { create_date: '#notnull' }
    And match response contains { email: '##string' }
    And match response contains { active: '##number' }
    And match response contains { last_update: '#notnull' }
    And match response contains { store: '#object' }
    And match response.store contains { store_id: #number }
    And match response contains { address: '#object' }
    And match response.address contains { address_id: #number }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { customer_id: 1374, first_name: 'ころころ', last_name: 'ひそひそ' }
    When method put
    Then status 404
