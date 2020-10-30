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
@Table(name = "film_category")
@IdClass(FilmCategoryId::class)
data class FilmCategory(
    @Id
    @Column(name = "film_id")
    @JsonProperty("film_id")
    val filmId: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @Id
    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    @JsonProperty("category")
    val category: Category = Category(),
) {
    @PersistenceConstructor
    constructor() : this(filmId = null)

    fun withFIlmId(filmId: Int): FilmCategory = this.copy(filmId = filmId)

    fun withLastUpdate(lastUpdate: LocalDateTime): FilmCategory = this.copy(lastUpdate = lastUpdate)

    fun withCategory(category: Category): FilmCategory = this.copy(category = category)

}

data class FilmCategoryId(
    val filmId: Int,
    val category: Int,
) : Serializable {
    companion object {
        const val serialVersionUID = 1374
    }
}

interface FilmCategoryRepository : CrudRepository<FilmCategory, FilmCategoryId> {
    fun findAllByOrderByFilmIdAscCategoryAsc(): List<FilmCategory>
}
