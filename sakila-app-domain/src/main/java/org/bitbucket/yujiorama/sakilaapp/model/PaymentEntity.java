package org.bitbucket.yujiorama.sakilaapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    @JsonProperty("payment_id")
    private Integer id;

    @Column(name = "payment_date", nullable = false)
    @JsonProperty("payment_date")
    private LocalDateTime paymentDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id", unique = true, nullable = false)
    @JsonProperty("customer")
    private CustomerEntity customer;

    @OneToOne(optional = false)
    @JoinColumn(name = "staff_id", unique = true, nullable = false)
    @JsonProperty("staff")
    private StaffEntity staff;

    @OneToOne(optional = false)
    @JoinColumn(name = "rental_id", unique = true, nullable = false)
    @JsonProperty("rental")
    private RentalEntity rental;

    @Column(name = "amount", nullable = false)
    @JsonProperty("amount")
    private BigDecimal amount;
}
