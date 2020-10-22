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
@Table(name = "language")
@Data
@AllArgsConstructor
@With
public class Language implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Language() {
    }

    @Id
    @SequenceGenerator(name = "language_language_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    @JsonProperty("language_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    private LocalDateTime lastUpdate;

    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
