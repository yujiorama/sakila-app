package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "actor")
@Data
@AllArgsConstructor
@With
public class Actor implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Actor() {
    }

    @Id
    @SequenceGenerator(name = "actor_actor_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    @JsonProperty("actor_id")
    private Long id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat("yyyy-MM-ddTHH:mm:ss.SSSZ")
    private LocalDateTime lastUpdate;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
