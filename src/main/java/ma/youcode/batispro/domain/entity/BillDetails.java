package ma.youcode.batispro.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class BillDetails {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice;
    private Double priceUnit;
    private Integer quantity;


    @OneToOne
    private Bill bill;



}
