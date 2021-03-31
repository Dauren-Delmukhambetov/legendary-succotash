package by.itechart.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @ApiModelProperty(
            value = "User's new first name",
            name = "firstName",
            dataType = "String",
            example = "Adam")
    String firstName;

    @ApiModelProperty(
            value = "User's new last name",
            name = "lastName",
            dataType = "String",
            example = "Smith")
    String lastName;

    @ApiModelProperty(
            value = "User's new email",
            name = "email",
            dataType = "String",
            example = "adam.smith@gmail.com")
    String email;

    @ApiModelProperty(
            value = "User's new password",
            name = "password",
            dataType = "String",
            example = "Password123")
    String password;

    @ApiModelProperty(
            value = "User's new phone number",
            name = "phone",
            dataType = "String",
            example = "+375 (29) 678-52-92")
    String phone;
}
