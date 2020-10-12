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
@Table(name = "staff")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class StaffEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "staff_id")
    @JsonProperty("staff_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false, unique = true)
    @JsonProperty("address")
    private AddressEntity address;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false, unique = true)
    @JsonProperty("store")
    private StoreEntity store;

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
}
