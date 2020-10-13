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
@Table(name = "store")
@Data
@AllArgsConstructor
@NoArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@With
public class StoreEntity implements Serializable {

    private static final long serialVersionUID = 1374L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    @JsonProperty("store_id")
    private Integer id;

    @Column(name = "last_update", nullable = false)
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    @JsonProperty("address")
    private AddressEntity address;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "manager_staff_id", referencedColumnName = "staff_id", unique = true, nullable = false)
    @JsonProperty("manager_staff")
    private StaffEntity managerStaff;
}
