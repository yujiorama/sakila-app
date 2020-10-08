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
@Table(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class RentalEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    @JsonProperty("payment_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @OneToOne(optional = false)
    @JoinColumn(name = "inventory_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("inventory")
    private InventoryEntity inventory;

    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("customer")
    private CustomerEntity customer;

    @Column(name = "return_date")
    @JsonProperty("return_date")
    private LocalDateTime returnDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "staff_id", unique = true, nullable = false, updatable = false)
    @JsonProperty("staff")
    private StaffEntity staff;
}
