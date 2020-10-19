package org.bitbucket.yujiorama.sakilaapp.test

import org.bitbucket.yujiorama.sakilaapp.model.Language
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SchemaUtilsTest {

    @Test
    fun `test generate`() {

        val actual = generate(Language::class.java.canonicalName)
        Assertions.assertEquals("""
            {
              "${'$'}schema":"http://json-schema.org/draft/2019-09/schema#",
              "title":"Language",
              "type":"object",
              "additionalProperties":false,
              "properties":{
                "language_id":{
                  "type":"integer"
                },
                "last_update":{
                  "type":"string",
                  "format":"date-time"
                },
                "name":{
                  "type":"string"
                }
              }
            }
            """.split("\n").joinToString("") { it.trim() }, actual)
    }

    @Test
    fun `test isValid`() {
        val json = """
            {
              "language_id": 1374,
              "name": "Tokyo",
              "last_update": "2020-01-02T03:04:05.678"
            }
        """

        val jsonSchema = generate(Language::class.java.canonicalName)

        Assertions.assertTrue(isValid(json, jsonSchema), "json=[${json}], jsonSchema=[${jsonSchema}]")
    }
}