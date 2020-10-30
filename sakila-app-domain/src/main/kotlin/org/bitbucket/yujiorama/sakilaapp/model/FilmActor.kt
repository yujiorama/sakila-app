package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "film_actor")
@IdClass(FilmActorId::class)
data class FilmActor(
    @Id
    @Column(name = "actor_id")
    @JsonProperty("actor_id")
    val actorId: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Id
    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    @JsonProperty("film")
    val film: Film = Film()
) {
    @PersistenceConstructor
    constructor() : this(actorId = null)

    fun withActorId(actorId: Int): FilmActor = this.copy(actorId = actorId)

    fun withLastUpdate(lastUpdate: LocalDateTime): FilmActor = this.copy(lastUpdate = lastUpdate)

    fun withFilm(film: Film): FilmActor = this.copy(film = film)
}

data class FilmActorId(
    val actorId: Int,
    val film: Int,
) : Serializable {

    companion object {
        const val serialVersionUID = 1374L
    }
}

interface FilmActorRepository : CrudRepository<FilmActor, FilmActorId> {
    fun findAllByOrderByActorIdAscFilmAsc(): List<FilmActor>
}
