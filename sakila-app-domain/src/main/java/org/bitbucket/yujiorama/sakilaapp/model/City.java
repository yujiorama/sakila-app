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
@Table(name = "city")
@Data
@AllArgsConstructor
@With
public class City implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public City() {
    }

    @Id
    @SequenceGenerator(name = "city_city_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    @JsonProperty("city_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat("yyyy-MM-ddTHH:mm:ss.SSSZ")
    private LocalDateTime lastUpdate;

    @Column(name = "city", nullable = false)
    @JsonProperty("city")
    private String city;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "country_id", referencedColumnName = "country_id", nullable = false)
    @JsonProperty("country")
    private Country country;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
