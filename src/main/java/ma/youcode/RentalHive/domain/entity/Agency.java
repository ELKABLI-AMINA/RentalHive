package ma.youcode.RentalHive.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Agency {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String local;
    @Column(columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean is_enabled = true;
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Client client;

    public Agency(Long id, String local, Client client) {
        this.id = id;
        this.local = local;
        this.client = client;
    }
}
