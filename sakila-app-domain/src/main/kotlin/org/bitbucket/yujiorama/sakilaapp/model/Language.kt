package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Table("language")
data class Language(
    @Id
    @Column("language_id")
    @JsonProperty("language_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column("name")
    @JsonProperty("name")
    val name: String = "",
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Int?): Language = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Language = this.copy(lastUpdate = lastUpdate)

    fun withName(name: String): Language = this.copy(name = name)
}

interface LanguageRepository : CrudRepository<Language, Int> {
    fun findAllByOrderByNameAsc(): List<Language>
}
