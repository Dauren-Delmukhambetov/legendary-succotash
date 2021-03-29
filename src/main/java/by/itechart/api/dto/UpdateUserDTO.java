package by.itechart.api.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
}
