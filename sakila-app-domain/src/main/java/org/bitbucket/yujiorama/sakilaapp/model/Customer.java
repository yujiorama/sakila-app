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
@Table(name = "customer")
@Data
@AllArgsConstructor
@With
public class Customer implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Customer() {
    }

    @Id
    @SequenceGenerator(name = "customer_customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    @JsonSchemaFormat("yyyy-MM-ddTHH:mm:ss.SSSZ")
    private LocalDateTime lastUpdate;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    @JsonProperty("store")
    private Store store;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @JsonProperty("address")
    private Address address;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "activebool", nullable = false)
    @JsonProperty("activebool")
    private Boolean activebool;

    @Column(name = "create_date", nullable = false)
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @Column(name = "active")
    @JsonProperty("active")
    private Integer active;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
