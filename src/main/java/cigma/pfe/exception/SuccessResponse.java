package cigma.pfe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SuccessResponse {

    private HttpStatus status;
    private String success;
    private String message;
    private LocalDateTime timeStamp;

}
