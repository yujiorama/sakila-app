package org.bitbucket.yujiorama.sakilaapp.test

import com.fasterxml.jackson.databind.json.JsonMapper
import com.github.fge.jackson.JsonLoader
import com.github.fge.jsonschema.main.JsonSchemaFactory
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig
import com.kjetland.jackson.jsonSchema.JsonSchemaDraft
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

fun customJsonSchemaConfig(): JsonSchemaConfig {

    val vanillaConfig = JsonSchemaConfig.vanillaJsonSchemaDraft4()
    return JsonSchemaConfig.create(
        vanillaConfig.autoGenerateTitleForProperties(),
        Optional.of("table"),
        vanillaConfig.useOneOfForOption(),
        vanillaConfig.useOneOfForNullables(),
        vanillaConfig.usePropertyOrdering(),
        vanillaConfig.hidePolymorphismTypeProperty(),
        vanillaConfig.disableWarnings(),
        vanillaConfig.useMinLengthForNotNull(),
        vanillaConfig.useTypeIdForDefinitionName(),
        HashMap(),
        vanillaConfig.useMultipleEditorSelectViaProperty(),
        setOf<Class<Any>>(),
        mapOf(
            LocalDateTime::class.java to String::class.java
        ),
        HashMap(),
        vanillaConfig.subclassesResolver(),
        vanillaConfig.failOnUnknownProperties(),
        null
    ).withJsonSchemaDraft(JsonSchemaDraft.DRAFT_2019_09)
}

fun generate(canonicalName: String): String {

    val objectMapper = JsonMapper.builder()
        .findAndAddModules()
        .build()
    val generator = JsonSchemaGenerator(objectMapper, true, customJsonSchemaConfig())
    val klass = ClassLoader.getSystemClassLoader().loadClass(canonicalName)
    val schema = generator.generateJsonSchema(klass)
    return objectMapper.writeValueAsString(schema)
}

fun isValid(json: String, jsonSchema: String): Boolean {

    val factory = JsonSchemaFactory.byDefault()
    val schema = factory.getJsonSchema(JsonLoader.fromString(jsonSchema))
    val report = schema.validate(JsonLoader.fromString(json))

    if (report.isSuccess.not()) {
        val logger = LoggerFactory.getLogger("SchemaUtils")
        logger.error("validation error: {}", report)
        logger.info("schema: {}", jsonSchema)
    }

    return report.isSuccess
}
