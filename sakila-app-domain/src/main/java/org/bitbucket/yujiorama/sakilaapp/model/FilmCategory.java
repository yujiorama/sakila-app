package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import org.bitbucket.yujiorama.sakilaapp.DateTimePattern;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "film_category")
@IdClass(FilmCategoryId.class)
@Data
@AllArgsConstructor
@With
public class FilmCategory implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public FilmCategory() {
    }

    @Id
    @Column(name = "film_id")
    @JsonProperty("film_id")
    private Integer filmId;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    private LocalDateTime lastUpdate;

    @Id
    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    @JsonProperty("category")
    private Category category;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
