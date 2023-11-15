package spharos.settle.global.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {

    private final boolean success;
    private final int code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .success(false)
                        .code(responseCode.getCode())
                        .message(responseCode.getMessage())
                        .build()
                );
    }

}
