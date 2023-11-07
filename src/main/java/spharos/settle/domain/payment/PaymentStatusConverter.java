package spharos.settle.domain.payment;

import jakarta.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(PaymentStatus.class).stream()
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 포인트 상태입니다."));
    }

}
