package spharos.settle.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spharos.settle.global.common.response.ResponseCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

}
