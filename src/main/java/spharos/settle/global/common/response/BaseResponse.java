package spharos.settle.global.common.response;

public record BaseResponse<T>(Boolean success, int code, T result) {

    // 요청에 성공한 경우 -> return 객체가 필요한 경우
    public BaseResponse(T result) {
        this(true, 200, result);
    }

    // 요청에 성공한 경우 -> return 객체가 필요 없는 경우
    public BaseResponse() {
        this(true, 200, null);
    }



}
