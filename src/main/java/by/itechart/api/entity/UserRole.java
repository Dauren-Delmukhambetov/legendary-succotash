package by.itechart.api.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "user_roles")
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "userRole")
    private User user;

}
