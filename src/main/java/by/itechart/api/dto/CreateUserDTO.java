package by.itechart.api.dto;

import by.itechart.api.configuration.util.annotation.Telephone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CreateUserDTO {

    @ApiModelProperty(
            value = "User's first name",
            name = "firstName",
            dataType = "String",
            example = "Adam")
    @Size(min = 2, max = 10, message = "First name must be between 2 and 10 characters")
    String firstName;

    @ApiModelProperty(
            value = "User's last name",
            name = "lastName",
            dataType = "String",
            example = "Smith")
    @Size(min = 2, max = 25, message = "Last name must be between 2 and 25 characters")
    String lastName;

    @ApiModelProperty(
            value = "User's email",
            name = "email",
            dataType = "String",
            example = "adam.smith@gmail.com",
            required = true)
    @Email(message = "Email should be valid")
    String email;

    @ApiModelProperty(
            value = "User's password",
            name = "password",
            dataType = "String",
            example = "Password123",
            required = true)
    @NotNull(message = "Password cannot be null")
    String password;

    @ApiModelProperty(
            value = "User's phone number",
            name = "phone",
            dataType = "String",
            example = "+375 (29) 678-52-92")
    @Telephone
    String phone;
}
