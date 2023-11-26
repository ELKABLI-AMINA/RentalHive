package ma.youcode.batispro.domain.entity;

import lombok.*;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@Builder
public class EquipmentUnit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ref;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;


    @Override
    public String toString() {
        return "EquipmentUnit{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
