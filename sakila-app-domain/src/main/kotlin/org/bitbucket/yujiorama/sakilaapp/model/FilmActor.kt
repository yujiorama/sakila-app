package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

@Table("film_actor")
data class FilmActor(
    // TODO composite primary key (actor_id, film_id)
    @Id
    val id: FilmActorId?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "film_id")
    @JsonProperty("film")
    val film: Film = Film(),
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: FilmActorId?): FilmActor = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): FilmActor = this.copy(lastUpdate = lastUpdate)

    fun withFilm(film: Film): FilmActor = this.copy(film = film)
}

data class FilmActorId(
    @Column("actor_id")
    @JsonProperty("actor_id")
    val actorId: Int,

    @Column("film_id")
    @JsonProperty("film_id")
    val filmId: Int,
)

interface FilmActorRepository : CrudRepository<FilmActor, FilmActorId> {
    fun findAllByOrderByIdAscFilmAsc(): List<FilmActor>
}
