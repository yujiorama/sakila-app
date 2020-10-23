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

@Table("actor")
data class Actor(
    @Id
    @Column("actor_id")
    @JsonProperty("actor_id")
    val id: Long?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column("first_name")
    @JsonProperty("first_name")
    val firstName: String = "",

    @Column("last_name")
    @JsonProperty("last_name")
    val lastName: String = "",
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: Long?): Actor = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Actor = this.copy(lastUpdate = lastUpdate)

    fun withFirstName(firstName: String): Actor = this.copy(firstName = firstName)

    fun withLastName(lastName: String): Actor = this.copy(lastName = lastName)
}

interface ActorRepository : CrudRepository<Actor, Long> {

    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Actor>
}
