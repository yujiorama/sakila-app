Feature: staff

  Background:
    * url appUrl + '/staffs'
    * def schema = {}
    * set schema.staff = read('schema/sakila/staff.json')
    * set schema.address = read('schema/sakila/address.json')
    * set schema.store = read('schema/sakila/store.json')

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And match response == schema.staff
    And match response.address == schema.address
    And match response.store == schema.store
    And match response contains { staff_id: 1 }

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
    And match response == schema.staff
    And match response.address == schema.address
    And match response.store == schema.store
    And match response contains { staff_id: 2 }
    * def staffId = response.staff_id

    Given path staffId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374001
    When method delete
    Then status 404

  @ignore @create
  Scenario: create
    Given def staff = { address: { address_id: 1374001 }, store: { store_id: 1374001 } }
    And request staff
    When method post
    Then status 200
    And match response == schema.staff
    And match response.address == schema.address
    And match response.store == schema.store

  @ignore @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And match response == schema.staff
    And match response.address == schema.address
    And match response.store == schema.store
    And match response contains { staff_id: 3 }
    * def staff = response
    * set staff.first_name = 'ころころ'

    Given path staff.staff_id
    And request staff
    When method put
    Then status 201
    And match response == schema.staff
    And match response.address == schema.address
    And match response.store == schema.store
    And match response contains { staff_id: 3 }
    And match response contains { first_name: 'ころころ' }

  @update
  Scenario: update(not found)
    Given path 1374001
    And request
      """
      {
        "staff_id": 1374001,
        "last_update": "2020-01-02T03:04:05.678",
        "first_name": "Mike",
        "last_name": "Hillyer",
        "email": "Mike.Hillyer@sakilastaff.com",
        "active": true,
        "username": "Mike",
        "password": "8cb2237d0679ca88db6464eac60da96345513964",
        "picture": null,
        "address": {
          "address_id": 1374001,
          "last_update": "2020-01-02T03:04:05.678",
          "address": "ころころ",
          "district": "ひそひそ",
          "city": {
            "city_id": 1374001,
            "last_update": "2020-01-02T03:04:05.678",
            "city": "Lethbridge",
            "country": {
              "country_id": 1374001,
              "last_update": "2020-01-02T03:04:05.678",
              "country": "Canada"
            }
          },
          "phone": " ",
          "address2": null,
          "postal_code": null
        },
        "store": {
          "store_id": 1374001,
          "last_update": "2020-01-02T03:04:05.678",
          "address": {
            "address_id": 1374001,
            "last_update": "2020-01-02T03:04:05.678",
            "address": "47 MySakila Drive",
            "district": " ",
            "city": {
              "city_id": 1374001,
              "last_update": "2020-01-02T03:04:05.678",
              "city": "Lethbridge",
              "country": {
                "country_id": 1374001,
                "last_update": "2020-01-02T03:04:05.678",
                "country": "Canada"
              }
            },
            "phone": " ",
            "address2": null,
            "postal_code": null
          }
        }
      }
      """
    When method put
    Then status 404
