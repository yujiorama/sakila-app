package org.bitbucket.yujiorama.sakilaapp.test

import org.bitbucket.yujiorama.sakilaapp.model.Language
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SchemaUtilsTest {

    @Disabled
    @Test
    fun `generate`() {

        val actual = generate(Language::class.java.canonicalName)
        Assertions.assertEquals("""
            |{
            |  "${'$'}schema":"http://json-schema.org/draft-04/schema#",
            |  "title":"Language",
            |  "type":"object",
            |  "additionalProperties":false,
            |  "properties":{
            |    "language_id":{
            |      "type":"integer"
            |    },
            |    "last_update":{
            |      "type":"array",
            |      "items":{
            |        "type":"integer"
            |      }
            |    },
            |    "name":{
            |      "type":"string"
            |    }
            |}
            |""".trimMargin().trimIndent().replace("\n", ""), actual)
    }
}