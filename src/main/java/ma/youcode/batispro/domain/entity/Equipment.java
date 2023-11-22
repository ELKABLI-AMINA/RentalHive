package ma.youcode.batispro.domain.entity;

import lombok.*;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentStatus;
import ma.youcode.batispro.domain.enums.Equipment.EquipmentType;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Builder
public class Equipment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated( EnumType.STRING)
    private EquipmentType equipmentType;
    private String model;
    private String description ;
    private Double locationPrice;
    @Enumerated( EnumType.STRING)
    private EquipmentStatus equipmentStatus;


    public Equipment(Long id, String name, EquipmentType equipmentType, String model, String description, EquipmentStatus equipmentStatus) {
        this.id = id;
        this.name = name;
        this.equipmentType = equipmentType;
        this.model = model;
        this.description = description;
        this.equipmentStatus = equipmentStatus;
    }

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    List<EquipmentUnit> equipmentUnits;

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", equipmentType=" + equipmentType +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                ", equipmentStatus=" + equipmentStatus +
                '}';
    }
}
