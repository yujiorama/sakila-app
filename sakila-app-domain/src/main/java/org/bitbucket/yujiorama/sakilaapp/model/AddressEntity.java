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
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    @JsonProperty("address_id")
    private Long id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "address", nullable = false)
    @JsonProperty("address")
    private String address;

    @Column(name = "address2")
    @JsonProperty("address2")
    private String address2;

    @Column(name = "district", nullable = false)
    @JsonProperty("district")
    private String district;

    @OneToOne(optional = false)
    @JoinColumn(name = "city_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("city")
    private CityEntity city;

    @Column(name = "postal_code")
    @JsonProperty("postal_code")
    private String postalCode;

    @Column(name = "phone", nullable = false)
    @JsonProperty("phone")
    private String phone;
}
