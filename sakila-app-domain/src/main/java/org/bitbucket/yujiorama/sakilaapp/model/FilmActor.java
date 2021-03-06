package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "film_actor")
@Data
@AllArgsConstructor
@With
public class FilmActor implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public FilmActor() {
    }

    @Id
    @Column(name = "film_id")
    @JsonProperty("film_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    @JsonProperty("film")
    private Film film;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
