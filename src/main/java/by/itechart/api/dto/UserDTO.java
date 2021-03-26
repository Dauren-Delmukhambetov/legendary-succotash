package by.itechart.api.dto;

import by.itechart.api.configuration.util.annotation.Telephone;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserDTO {
    @Size(min = 2, max = 10, message = "First name must be between 2 and 10 characters")
    String firstName;
    @Size(min = 2, max = 25, message = "Last name must be between 2 and 25 characters")
    String lastName;
    @Email(message = "Email should be valid")
    String email;
    @NotNull(message = "Password cannot be null")
    String password;
    @Telephone
    String phone;
}