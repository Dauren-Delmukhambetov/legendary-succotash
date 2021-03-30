package by.itechart.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ApiModel(value = "User model")
public class UserDTO extends UpdateUserDTO {
    @ApiModelProperty(
            value = "User ID",
            name = "id",
            dataType = "Long",
            example = "19485",
            readOnly = true)
    Long id;

}
