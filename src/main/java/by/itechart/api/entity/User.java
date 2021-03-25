package by.itechart.api.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "user model")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 10, message = "First name must be between 2 and 10 characters")
    private String firstName;

    @Size(min = 2, max = 25, message = "Last name must be between 2 and 25 characters")
    private String lastName;

    @Column(nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    private String password;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phone;

    @Column(nullable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate deletedAt;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private UserRole userRole;

}
