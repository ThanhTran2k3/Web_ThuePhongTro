package webthuetro.otp_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import webthuetro.auth_service.dto.response.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.builder().build());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<String>> handlingAppException(AppException exception) {
        return ResponseEntity.badRequest().body(
                ApiResponse.<String>builder()
                    .success(false)
                    .time(LocalDateTime.now())
                    .error(exception.getMessage())
                    .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest().body(
                ApiResponse.<List<String>>builder()
                        .success(false)
                        .time(LocalDateTime.now())
                        .error(errors)
                        .build());
    }
}
