package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern;
import org.bitbucket.yujiorama.sakilaapp.impl.RatingEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "film")
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class,
        defaultForType = String[].class
    ),
    @TypeDef(
        name = "mpaa_rating",
        typeClass = RatingEnumType.class
    )
})
@Data
@AllArgsConstructor
@With
public class Film implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Film() {
    }

    @Id
    @SequenceGenerator(name = "film_film_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    @JsonProperty("film_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    private LocalDateTime lastUpdate;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    @JsonProperty("language")
    private Language language;

    @SuppressWarnings("DefaultAnnotationParam")
    @OneToOne(
        optional = true,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "original_language_id", referencedColumnName = "language_id")
    @JsonProperty("original_language")
    private Language originalLanguage;

    @Column(name = "title", nullable = false)
    @JsonProperty("title")
    private String title;

    @Column(name = "rental_duration", nullable = false)
    @JsonProperty("rental_duration")
    private Integer rentalDuration;

    @Column(name = "rental_rate", nullable = false)
    @JsonProperty("rental_rate")
    private BigDecimal rentalRate;

    @Column(name = "replacement_cost", nullable = false)
    @JsonProperty("replacement_cost")
    private BigDecimal replacementCost;

    @Column(name = "rating")
    @Type(type = "mpaa_rating")
    @JsonProperty("rating")
    private Rating rating;

    @Column(name = "special_features", nullable = false, columnDefinition = "text[]")
    @JsonProperty("special_features")
    @Type(type = "string-array")
    private String[] specialFeatures;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "release_year")
    @JsonProperty("release_year")
    private Integer releaseYear;

    @Column(name = "length")
    @JsonProperty("length")
    private Integer length;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
