Feature: language

  Background:
    * url appUrl + '/languages'
    * def SchemaUtils = Java.type('org.bitbucket.yujiorama.sakilaapp.test.SchemaUtilsKt')
    * def schema = {}
    * set schema.language = SchemaUtils.generate('org.bitbucket.yujiorama.sakilaapp.model.Language')

  @read
  Scenario: read(found)
    Given path 1
    When method get
    Then status 200
    And assert SchemaUtils.isValid(response, schema.language)
    And match response contains { language_id: 1 }

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
    And assert SchemaUtils.isValid(response, schema.language)
    And match response contains { language_id: 2 }
    * def languageId = response.language_id

    Given path languageId
    When method delete
    Then status 204

  @delete
  Scenario: delete(not found)
    Given path 1374001
    When method delete
    Then status 404

  @create
  Scenario: create
    Given def language = { name: 'からから' }
    And request language
    When method post
    Then status 200
    And assert SchemaUtils.isValid(response, schema.language)
    And match response contains { name: 'からから' }

  @update
  Scenario: update(found)
    Given path 3
    When method get
    Then status 200
    And assert SchemaUtils.isValid(response, schema.language)
    And match response contains { language_id: 3 }
    * def language = response
    * set language.name = 'こそこそ'

    Given path language.language_id
    And request language
    When method put
    Then status 201
    And assert SchemaUtils.isValid(response, schema.language)
    And match response contains { language_id: 3 }
    And match response contains { name: 'こそこそ' }

  @update
  Scenario: update(not found)
    Given path 1374001
    And request { language_id: 1374001, name: 'ひそひそ' }
    When method put
    Then status 404
