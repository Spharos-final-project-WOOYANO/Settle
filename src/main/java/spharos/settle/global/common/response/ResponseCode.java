package spharos.settle.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 200: 요청 성공
     **/
    SUCCESS(HttpStatus.OK,true, 200, "요청에 성공하였습니다."),


    /**
     * 에러 코드
     */
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, false, 1010, "아이디 또는 비밀번호를 확인해 주세요.");


    private final HttpStatus httpStatus;
    private final boolean success;
    private final int code;
    private final String message;

}
