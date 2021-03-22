package by.itechart.api.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
}