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
@Table(name = "category")
@Data
@AllArgsConstructor
@With
public class Category implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Category() {
    }

    @Id
    @SequenceGenerator(name = "category_category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("category_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat("yyyy-MM-ddTHH:mm:ss.SSSZ")
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
