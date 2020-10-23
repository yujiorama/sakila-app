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

@Table("film_category")
data class FilmCategory(
    // TODO composite primary key (film_id, category_id)
    @Id
    val id: FilmCategoryId?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "category_id")
    @JsonProperty("category")
    val category: Category = Category(),
) {
    @PersistenceConstructor
    constructor() : this(id = null)

    fun withId(id: FilmCategoryId?): FilmCategory = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): FilmCategory = this.copy(lastUpdate = lastUpdate)

    fun withCategory(category: Category): FilmCategory = this.copy(category = category)
}

data class FilmCategoryId(
    @Column("film_id")
    @JsonProperty("film_id")
    val filmId: Int,

    @Column("category_id")
    @JsonProperty("category_id")
    val categoryId: Int,
)

interface FilmCategoryRepository : CrudRepository<FilmCategory, FilmCategoryId> {
    fun findAllByOrderByIdAscCategoryAsc(): List<FilmCategory>
}
