package by.itechart.api.dto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class UserDTO extends UpdateUserDTO {
    Long id;
}
