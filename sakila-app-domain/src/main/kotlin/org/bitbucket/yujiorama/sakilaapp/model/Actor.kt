package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "actor")
data class Actor(
    @Id
    @SequenceGenerator(name = "actor_actor_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    @JsonProperty("actor_id")
    val id: Long?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    val firstName: String = "",

    @Column(name = "last_name", nullable = false)
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

interface ActorRepository : CrudRepository<Actor, Long?> {
    fun findByFirstNameOrderByFirstName(firstName: String): List<Actor>

    fun findAllByOrderByFirstNameAscLastNameAsc(): List<Actor>
}
