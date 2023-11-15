package ma.youcode.RentalHive.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer quantity;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Boolean is_returned = false;
    @ManyToOne
    private EquipmentUnit equipmentUnit;
}