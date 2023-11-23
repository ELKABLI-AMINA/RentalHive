package ma.youcode.RentalHive.domain.entity;

import lombok.*;
import ma.youcode.RentalHive.domain.enums.Location.LocationStatus;
import ma.youcode.RentalHive.domain.enums.PaymentStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "reservations")
@Getter
@Setter
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private UUID reference;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private LocationStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @ManyToOne()
    @JoinTable(name = "equipmentunit_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "equipmentunit_id"))
    private EquipmentUnit equipmentUnit;

    @ManyToOne
    private Equipment equipment;

    @ManyToOne
    private DossierLocation dossierLocation;
}
