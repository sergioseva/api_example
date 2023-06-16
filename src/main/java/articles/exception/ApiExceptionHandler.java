package articles.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Optional: Change if needed
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiError> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error ->
                        errors.add(new ApiError(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage(), ((FieldError) error).getField(), new Date()))
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ApiError> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error ->
                        errors.add(new ApiError(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage(), ((FieldError) error).getField(), new Date()))
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
