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
@IdClass(FilmActorId.class)
@Data
@AllArgsConstructor
@With
public class FilmActor implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public FilmActor() {
    }

    @Id
    @Column(name = "actor_id")
    @JsonProperty("actor_id")
    private Integer actorId;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @Id
    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
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
