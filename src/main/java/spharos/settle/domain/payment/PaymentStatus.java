package spharos.settle.domain.payment;


import spharos.settle.global.CodeValue;

public enum PaymentStatus implements CodeValue {
    DONE("0","결제 완료"),
    CANCEL("1","결제 취소"),

    SETTLED("2","정산 완료");

    private final String code;
    private final String value;

    PaymentStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
