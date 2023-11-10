package spharos.settle.domain.settle;


import spharos.settle.global.CodeValue;

public enum SettleStatus implements CodeValue {
    DEPOSIT_COMPLETED("DEPOSIT_COMPLETED", "정산 완료"),
    DEPOSIT_SCHEDULED("DEPOSIT_SCHEDULED", "정산 예정"),;



    private final String code;
    private final String value;

    SettleStatus(String code, String value) {
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
