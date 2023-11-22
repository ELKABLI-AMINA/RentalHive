package ma.youcode.batispro.domain.entity;

import lombok.*;
import ma.youcode.batispro.domain.enums.BillStatus;
import ma.youcode.batispro.domain.enums.PaymentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String billNumber;
    private LocalDateTime dateCreation;
    @Enumerated(EnumType.STRING)
    private BillStatus status;
    private PaymentStatus paymentStatus;
    private LocalDateTime dateConfirmation;
    private String comment ;


    @OneToOne(cascade = CascadeType.ALL)
    private BillDetails billDetails;




    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    private DossierLocation dossierLocation;


    public Double getTotal(){
        Objects.requireNonNull(dossierLocation);
        return dossierLocation.getLocation()
                .stream()
                .map(location -> location.getEquipment().getLocationPrice() * location.getQuantity())
                .reduce(0.0, Double::sum);
    }



}
