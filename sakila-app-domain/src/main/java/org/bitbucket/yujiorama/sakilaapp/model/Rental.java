package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Data
@AllArgsConstructor
@With
public class Rental implements Serializable {

    private static final long serialVersionUID = 1374L;

    @PersistenceConstructor
    public Rental() {
    }

    @Id
    @SequenceGenerator(name = "rental_rental_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    @JsonProperty("rental_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id", nullable = false)
    @JsonProperty("inventory")
    private Inventory inventory;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @JsonProperty("customer")
    private Customer customer;

    @OneToOne(
        optional = false,
        cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id", nullable = false)
    @JsonProperty("staff")
    private Staff staff;

    @Column(name = "return_date")
    @JsonProperty("return_date")
    private LocalDateTime returnDate;

    @PrePersist
    void preInsert() {

        if (this.lastUpdate == null) {
            this.lastUpdate = LocalDateTime.now();
        }
    }
}
