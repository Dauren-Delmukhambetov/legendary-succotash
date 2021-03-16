package by.itechart.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Id;

public record UserDTO(@Id @JsonProperty("id") Long id, @JsonProperty("firstName") String firstname,
                      @JsonProperty("lastName") String lastname, @JsonProperty("email") String email,
                      @JsonProperty("password") String password, @JsonProperty("phone") String phone
                     ) {

}