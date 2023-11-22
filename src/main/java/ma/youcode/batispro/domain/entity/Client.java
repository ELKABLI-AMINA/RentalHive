package ma.youcode.batispro.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Builder
public class Client extends Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String phone;
    private String cin;

    public Client(String name, String email, ma.youcode.batispro.domain.enums.UserRole UserRole, boolean enable, String address, String phone, String cin) {
        super(name, email, UserRole, enable);
        this.address = address;
        this.phone = phone;
        this.cin = cin;
    }
}
