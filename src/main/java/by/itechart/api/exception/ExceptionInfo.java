package by.itechart.api.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionInfo {
  @NonNull
  private String message;
  private HttpStatus status;
  private List<String> errors;

}
