package org.bitbucket.yujiorama.sakilaapp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat
import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern
import org.bitbucket.yujiorama.sakilaapp.impl.RatingEnumType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.repository.CrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "film")
@TypeDefs(
    TypeDef(
        name = "string-array",
        typeClass = StringArrayType::class,
        defaultForType = Array<String>::class
    ),
    TypeDef(
        name = "mpaa_rating",
        typeClass = RatingEnumType::class
    )
)
data class Film(
    @Id
    @SequenceGenerator(name = "film_film_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    @JsonProperty("film_id")
    val id: Int?,

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    val lastUpdate: LocalDateTime = LocalDateTime.now(),

    @OneToOne(
        optional = false,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    @JsonProperty("language")
    val language: Language = Language(),

    @OneToOne(
        optional = true,
        cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name = "original_language_id", referencedColumnName = "language_id", nullable = true)
    @JsonProperty("original_language")
    val originalLanguage: Language? = null,

    @Column(name = "title", nullable = false)
    @JsonProperty("title")
    var title: String = "",

    @Column(name = "rental_duration", nullable = false)
    @JsonProperty("rental_duration")
    val rentalDuration: Int = 0,

    @Column(name = "rental_rate", nullable = false)
    @JsonProperty("rental_rate")
    val rentalRate: BigDecimal = BigDecimal.ZERO,

    @Column(name = "replacement_cost", nullable = false)
    @JsonProperty("replacement_cost")
    val replacementCost: BigDecimal = BigDecimal.ZERO,

    @Column(name = "rating")
    @Type(type = "mpaa_rating")
    @JsonProperty("rating")
    val rating: Rating = Rating.G,

    @Column(name = "special_features", nullable = false, columnDefinition = "text[]")
    @JsonProperty("special_features")
    @Type(type = "string-array")
    val specialFeatures: Array<String> = emptyArray(),

    @Column(name = "description")
    @JsonProperty("description")
    val description: String? = null,

    @Column(name = "release_year")
    @JsonProperty("release_year")
    val releaseYear: Int? = null,

    @Column(name = "length")
    @JsonProperty("length")
    val length: Int? = null,
) {
    @PersistenceConstructor
    constructor() : this(id = null)

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

interface FilmRepository : CrudRepository<Film, Int?> {
    fun findAllByOrderByTitleAsc(): List<Film>
}
