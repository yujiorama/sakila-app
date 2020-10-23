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
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("film")
data class Film(
    @Id
    @Column("film_id")
    @JsonProperty("film_id")
    val id: Int?,

    @Column("last_update")
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @MappedCollection(idColumn = "language_id", keyColumn = "language_id")
    @JsonProperty("language")
    val language: Language = Language(),

    @MappedCollection(idColumn = "language_id", keyColumn = "original_language_id")
    @JsonProperty("original_language")
    val originalLanguage: Language?,

    @Column("title")
    @JsonProperty("title")
    val title: String = "",

    @Column("rental_duration")
    @JsonProperty("rental_duration")
    val rentalDuration: Int = -1,

    @Column("rental_rate")
    @JsonProperty("rental_rate")
    val rentalRate: BigDecimal = BigDecimal.ZERO,

    @Column("replacement_cost")
    @JsonProperty("replacement_cost")
    val replacementCost: BigDecimal = BigDecimal.ZERO,

    @Column("rating")
    @JsonProperty("rating")
    val rating: Rating = Rating.G,

    @Column("special_features")
    @JsonProperty("special_features")
    val specialFeatures: Array<String> = emptyArray(),

    @Column("description")
    @JsonProperty("description")
    val description: String?,

    @Column("release_year")
    @JsonProperty("release_year")
    val releaseYear: Int?,

    @Column("length")
    @JsonProperty("length")
    val length: Int?,
) {
    @PersistenceConstructor
    constructor() : this(id = null, originalLanguage = null, description = null, releaseYear = null, length = null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Film

        if (id != other.id) return false
        if (lastUpdate != other.lastUpdate) return false
        if (language != other.language) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (rentalDuration != other.rentalDuration) return false
        if (rentalRate != other.rentalRate) return false
        if (replacementCost != other.replacementCost) return false
        if (rating != other.rating) return false
        if (!specialFeatures.contentEquals(other.specialFeatures)) return false
        if (description != other.description) return false
        if (releaseYear != other.releaseYear) return false
        if (length != other.length) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + lastUpdate.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + (originalLanguage?.hashCode() ?: 0)
        result = 31 * result + title.hashCode()
        result = 31 * result + rentalDuration
        result = 31 * result + rentalRate.hashCode()
        result = 31 * result + replacementCost.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + specialFeatures.contentHashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (releaseYear ?: 0)
        result = 31 * result + (length ?: 0)
        return result
    }

    fun withId(id: Int?): Film = this.copy(id = id)

    fun withLastUpdate(lastUpdate: LocalDateTime): Film = this.copy(lastUpdate = lastUpdate)

    fun withLanguage(language: Language): Film = this.copy(language = language)

    fun withOriginalLanguage(originalLanguage: Language?): Film = this.copy(originalLanguage = originalLanguage)

    fun withTitle(title: String): Film = this.copy(title = title)

    fun withRentalDuration(rentalDuration: Int): Film = this.copy(rentalDuration = rentalDuration)

    fun withRentalRate(rentalRate: BigDecimal): Film = this.copy(rentalRate = rentalRate)

    fun withReplacementCost(replacementCost: BigDecimal): Film = this.copy(replacementCost = replacementCost)

    fun withRating(rating: Rating): Film = this.copy(rating = rating)

    fun withSpecialFeatures(specialFeatures: Array<String>): Film = this.copy(specialFeatures = specialFeatures)

    fun withDescription(description: String?): Film = this.copy(description = description)

    fun withReleaseYear(releaseYear: Int?): Film = this.copy(releaseYear = releaseYear)

    fun withLength(length: Int?): Film = this.copy(length = length)
}

interface FilmRepository : CrudRepository<Film, Int> {
    fun findAllByOrderByTitleAsc(): List<Film>
}
