package spharos.settle.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static spharos.settle.domain.payment.QPayment.payment;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spharos.settle.batch.expression.Expression;
import spharos.settle.batch.reader.QuerydslNoOffsetNumberOptions;
import spharos.settle.batch.reader.QuerydslNoOffsetOptions;
import spharos.settle.domain.payment.Payment;
import spharos.settle.domain.payment.PaymentStatus;
import spharos.settle.domain.payment.PaymentType;
import spharos.settle.dto.PaymentResult;
import spharos.settle.infrastructure.payment.PaymentRepository;


@SpringBootTest
class PaymentServiceTest {


    @Autowired
    private PaymentRepository paymentRepository; // Payment 엔터티를 다루는 레포지토리 인터페이스

    @Test
    public void 결제완료더미데이터저장() {
       // for(int i =1 ;i<10; i++){
            // Given
        String clientEmail = "test1";
        PaymentType paymentType = PaymentType.CARD;
        int totalAmount = 1000;
        LocalDateTime approvedAt = LocalDateTime.now();
        PaymentStatus paymentStatus = PaymentStatus.CANCEL;

            // When
        Payment payment = Payment.createPayment(clientEmail, paymentType, totalAmount, approvedAt, paymentStatus);
        Payment save = paymentRepository.save(payment);

        // Then
        //assertEquals(clientEmail, save.getClientEmail());
    }

    @Test
    public void 테스트이름() throws Exception {

        String requestDate = "2023-11-09";
        Map<String, Object> parameters = new LinkedHashMap<>();
        LocalDateTime parse = LocalDateTime.parse(requestDate + "T00:00:00");
        LocalDateTime parse1 = LocalDateTime.parse(requestDate + "T23:59:59");
        // given
        List<PaymentResult> teset = paymentRepository.teset(parse, parse1);
        // when
        System.out.println("teset.toString() = " + teset.toString());
        // then
    }


    @Test
    public void path변수에서_필드명을_추출한다() {
        // given
        String expected = "id";
        // when
        QuerydslNoOffsetNumberOptions<Payment, Long> options = new QuerydslNoOffsetNumberOptions<>(
                payment.id, Expression.ASC);
        // then
        assertEquals(expected, options.getFieldName());
    }

}

