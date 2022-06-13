package cigma.pfe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String error;
    private String message;
    private LocalDateTime timeStamp;

}
