package spharos.settle.domain.settle;

import jakarta.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import spharos.settle.domain.payment.PaymentStatus;

public class SettleStatusConverter implements AttributeConverter<SettleStatus, String> {

    @Override
    public String convertToDatabaseColumn(SettleStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public SettleStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(SettleStatus.class).stream()
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 포인트 상태입니다."));
    }

}
