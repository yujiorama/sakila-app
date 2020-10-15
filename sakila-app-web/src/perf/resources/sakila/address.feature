Feature: address
# {
#   "address_id": Int,
#   "address": String,
#   "district": String,
#   "city": {},
#   "phone": String,
#   "address2": String,
#   "postal_code": String,
#   "last_update": LocalDateTime
# }
  Background:
    * url appUrl + '/addresses'

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response contains { address_id: 1}
    And match response contains { address: '#string' }
    And match response contains { district: '#string' }
    And match response contains { city: '#object' }
    And match response contains { phone: '#string' }
    And match response contains { address2: '##string' }
    And match response contains { postal_code: '##string' }
    And match response contains { last_update: '#notnull' }
    And match response.city == { city_id: 300, city: '#string', country: '#object', last_update: '#notnull' }
    And match response.city.country == { country_id: 20, country: '#string', last_update: '#notnull' }

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
    And match response contains { address_id: 2 }
    And match response contains { address: '#string' }
    And match response contains { district: '#string' }
    And match response contains { city: '#object' }
    And match response contains { phone: '#string' }
    And match response contains { address2: '##string' }
    And match response contains { postal_code: '##string' }
    And match response contains { last_update: '#notnull' }
    And match response.city == { city_id: #number, city: '#string', country: '#object', last_update: '#notnull' }
    And match response.city.country == { country_id: #number, country: '#string', last_update: '#notnull' }
    And def addressId = response.address_id

    Given path addressId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def address = { address: 'そろそろ', district: 'わざわざ' }
    * set address.city = { city_id: 1374, city: 'するする' }
    * set address.city.country = { country_id: 1374, country: 'きらきら' }
    And request address
    When method post
    Then status 200
    And match response contains { address_id: #number }
    And match response contains { address: 'そろそろ' }
    And match response contains { district: 'わざわざ' }
    And match response contains { city: '#object' }
    And match response contains { phone: '#string' }
    And match response contains { address2: '##string' }
    And match response contains { postal_code: '##string' }
    And match response contains { last_update: '#notnull' }
    And match response.city == { city_id: #number, city: 'するする', country: '#object', last_update: '#notnull' }
    And match response.city.country == { country_id: #number, country: 'きらきら', last_update: '#notnull' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response contains { address_id: 3 }
    And match response contains { address: '#string' }
    And match response contains { district: '#string' }
    And match response contains { city: '#object' }
    And match response contains { phone: '#string' }
    And match response contains { address2: '##string' }
    And match response contains { postal_code: '##string' }
    And match response contains { last_update: '#notnull' }
    And match response.city == { city_id: #number, city: '#string', country: '#object', last_update: '#notnull' }
    And match response.city.country == { country_id: #number, country: '#string', last_update: '#notnull' }
    And def address = response
    * set address.address = 'ころころ'
    * set address.district = 'ひそひそ'

    Given path address.address_id
    And request address
    When method put
    Then status 201
    And match response contains { address_id: 3 }
    And match response contains { address: 'ころころ' }
    And match response contains { district: 'ひそひそ' }
    And match response contains { city: '#object' }
    And match response contains { phone: '#string' }
    And match response contains { address2: '##string' }
    And match response contains { postal_code: '##string' }
    And match response contains { last_update: '#notnull' }
    And match response.city == { city_id: #number, city: '#string', country: '#object', last_update: '#notnull' }
    And match response.city.country == { country_id: #number, country: '#string', last_update: '#notnull' }

  @update
  Scenario: update(not found)
    Given path 1374
    And request { address_id: 1374, address: 'ころころ', district: 'ひそひそ' }
    When method put
    Then status 404
