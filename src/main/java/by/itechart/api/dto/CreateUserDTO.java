package by.itechart.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDTO {

    @ApiModelProperty(
            value = "User's first name",
            name = "firstName",
            dataType = "String",
            example = "Adam")
    String firstName;

    @ApiModelProperty(
            value = "User's last name",
            name = "lastName",
            dataType = "String",
            example = "Smith")
    String lastName;

    @ApiModelProperty(
            value = "User's email",
            name = "email",
            dataType = "String",
            example = "adam.smith@gmail.com",
            required = true)
    String email;

    @ApiModelProperty(
            value = "User's password",
            name = "password",
            dataType = "String",
            example = "Password123",
            required = true)
    String password;

    @ApiModelProperty(
            value = "User's phone number",
            name = "phone",
            dataType = "String",
            example = "+375 (29) 678-52-92")
    String phone;
}
