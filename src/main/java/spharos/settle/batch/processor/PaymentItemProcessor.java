package spharos.settle.batch.processor;

import static java.util.stream.Collectors.summingInt;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import spharos.settle.domain.payment.Payment;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.domain.settle.SettleStatus;
import spharos.settle.dto.PaymentResult;
import spharos.settle.dto.PaymentResultResponseList;

@Component
@Slf4j
public class PaymentItemProcessor implements ItemProcessor<PaymentResult, DailySettle> {
    private static final double vat = 0.02;

    @Override
    public DailySettle process(PaymentResult item) throws Exception {
        String clientEmail = item.getClientEmail();
        long totalAmount = item.getTotalAmount();
        long fee = (long) (totalAmount * vat);
        long paymentAmount = totalAmount - fee;
        SettleStatus settleStatus = SettleStatus.DEPOSIT_SCHEDULED;
        return DailySettle.createSettle(clientEmail, totalAmount, LocalDate.now(),settleStatus,fee,paymentAmount);
    }
}