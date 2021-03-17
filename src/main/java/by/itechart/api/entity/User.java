package by.itechart.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Column(nullable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate deletedAt;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    @JsonBackReference
    private UserRole userRole;

}
