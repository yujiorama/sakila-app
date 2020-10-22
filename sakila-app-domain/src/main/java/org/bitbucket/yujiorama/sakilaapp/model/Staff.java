package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "staff")
@Data
@AllArgsConstructor
@With
public class Staff implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Staff() {
    }

    @Id
    @SequenceGenerator(name = "staff_staff_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    @JsonProperty("staff_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat(DateTimePattern.ENTITY_DATETIME_PATTERN)
    private LocalDateTime lastUpdate;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @JsonProperty("address")
    private Address address;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", unique = true, nullable = false)
    @JsonProperty("store")
    @JsonIgnoreProperties({"manager_staff"})
    private Store store;

    @Column(name = "active", nullable = false)
    @JsonProperty("active")
    private Boolean active;

    @Column(name = "username", nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(name = "password")
    @JsonProperty("password")
    private String password;

    @Column(name = "picture")
    @JsonProperty("picture")
    private byte[] picture;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
