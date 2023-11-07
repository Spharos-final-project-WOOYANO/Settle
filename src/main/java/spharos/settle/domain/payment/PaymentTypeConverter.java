package spharos.settle.domain.payment;

import jakarta.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class PaymentTypeConverter implements AttributeConverter<PaymentType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentType attribute) {
        return attribute.getCode();
    }


    @Override
    public PaymentType convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(PaymentType.class).stream()
                .filter(c -> c.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 포인트 상태입니다."));
    }
}
