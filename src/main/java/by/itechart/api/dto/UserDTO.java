package by.itechart.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import static io.swagger.annotations.ApiModelProperty.AccessMode.READ_ONLY;

@NoArgsConstructor
@Data
@ApiModel(value = "User model")
public class UserDTO {
    @ApiModelProperty(
            value = "User ID",
            name = "id",
            dataType = "Long",
            example = "19485",
            accessMode = READ_ONLY)
    Long id;

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

    @JsonIgnore
    @ApiModelProperty(
            notes = "User's password should not be exposed",
            hidden = true)
    String password;

    @ApiModelProperty(
            value = "User's phone number",
            name = "phone",
            dataType = "String",
            example = "+375 (29) 678-52-92")
    String phone;

}
