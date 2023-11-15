package spharos.settle.global.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spharos.settle.global.common.response.ErrorResponse;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<?> customExHandle(CustomException e) {
        log.error("[exceptionHandle] ex", e);
        return ErrorResponse.toResponseEntity(e.getResponseCode());
    }

//    @ExceptionHandler(value = { CustomException.class })
//    public ResponseEntity<ErrorResponse> customExHandle(CustomException e) {
//        log.error("[exceptionHandle] ex", e);
//        return ErrorResponse.toResponseEntity(e.getResponseCode());
//    }


}
