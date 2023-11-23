package ma.youcode.RentalHive.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.RentalHive.domain.enums.UserRole;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole UserRole;
    private boolean enable;


    public Users(String name, String email, ma.youcode.RentalHive.domain.enums.UserRole userRole, boolean enable) {
        this.name = name;
        this.email = email;
        UserRole = userRole;
        this.enable = enable;
    }
}
