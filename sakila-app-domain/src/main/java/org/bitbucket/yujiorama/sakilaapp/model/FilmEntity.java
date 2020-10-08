package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.bitbucket.yujiorama.sakilaapp.impl.RatingConverter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "film")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class FilmEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "film_id")
    @JsonProperty("film_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "title", nullable = false)
    @JsonProperty("title")
    private String title;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "release_year")
    @JsonProperty("release_year")
    private Integer releaseYear;

    @OneToOne(optional = false)
    @JoinColumn(name = "language_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("language")
    private LanguageEntity language;

    @OneToOne
    @JoinColumn(name = "language_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("original_language")
    private LanguageEntity originalLanguage;

    @Column(name = "length")
    @JsonProperty("length")
    private Integer length;

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
    @Convert(converter = RatingConverter.class)
    @JsonProperty("rating")
    private RatingEnum rating;

    @Column(name = "special_features", nullable = false)
    @JsonProperty("special_features")
    @Type(type = "org.bitbucket.yujiorama.sakilaapp.impl.GenericArrayUserType")
    private String[] specialFeatures;

    @Column(name = "fulltext", nullable = false)
    @JsonProperty("fulltext")
    private String fulltext;
}
