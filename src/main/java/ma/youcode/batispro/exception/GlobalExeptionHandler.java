package ma.youcode.batispro.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
@Slf4j
public class GlobalExeptionHandler {


@ExceptionHandler({EquipmentNotFoundException.class, DossierNotFoundException.class, EquipmentOutOfStockException.class, AgencyNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEquipmentNotFoundException(Exception ex, HttpServletRequest request){

    ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI()
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
}

     @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex, HttpServletRequest request){
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    ex.getMessage(),
                    request.getRequestURI()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
     }
}
