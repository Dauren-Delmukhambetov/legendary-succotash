package by.itechart.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import by.itechart.api.configuration.util.annotation.Telephone;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@ApiModel(value = "User model")
public class UserDTO {
    @ApiModelProperty(
            value = "User ID",
            name = "id",
            dataType = "Long",
            example = "19485",
            readOnly = true)
    Long id;

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
            notes = "User's password should not be exposed",
            hidden = true)
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
