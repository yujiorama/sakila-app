package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @OneToOne(optional = false)
    @JoinColumn(name = "store_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("store")
    private StoreEntity store;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @JsonProperty("address")
    private AddressEntity address;

    @Column(name = "activebool", nullable = false)
    @JsonProperty("activebool")
    private Boolean activebool;

    @Column(name = "create_date", nullable = false)
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @Column(name = "active")
    @JsonProperty("active")
    private Integer active;
}