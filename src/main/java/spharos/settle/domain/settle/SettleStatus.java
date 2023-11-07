package spharos.settle.domain.settle;


import spharos.settle.global.CodeValue;

public enum SettleStatus implements CodeValue {
    DEPOSIT_COMPLETED("0","입금완료"),
    DEPOSIT_SCHEDULED("1","입금예정");



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
