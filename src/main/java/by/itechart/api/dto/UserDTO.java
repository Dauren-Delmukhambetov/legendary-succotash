package by.itechart.api.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO {
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
}
