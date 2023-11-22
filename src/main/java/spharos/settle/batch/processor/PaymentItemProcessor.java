package spharos.settle.batch.processor;

import static java.util.stream.Collectors.summingInt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.domain.settle.SettleStatus;
import spharos.settle.dto.PaymentResult;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentItemProcessor implements ItemProcessor<String, DailySettle> {
    private static final double vat = 0.02;
    private final ObjectMapper objectMapper;

    @Override
    public DailySettle process(String item) throws Exception {
       /* DailySettle settle = null;
        //Map<String, Long> resultMap = objectMapper.readValue(item, HashMap.class);
        Map<String, Long> resultMap = objectMapper.readValue(item, new TypeReference<Map<String, Long>>() {});

        for (Map.Entry<String, Long> entry : resultMap.entrySet()) {
            String clientEmail = entry.getKey();
            Long totalAmount = entry.getValue();
            long fee = (long) (totalAmount * vat);
            long paymentAmount = totalAmount - fee;
            String settleStatus = "0";
            settle = DailySettle.createSettle(clientEmail, totalAmount, LocalDate.now(), settleStatus, fee,
                    paymentAmount);

        }
        log.info("settle : {}", settle);
        return settle;*/

        PaymentResult paymentResult = objectMapper.readValue(item, PaymentResult.class);
        log.info("paymentResult : {}", paymentResult);
        String clientEmail = paymentResult.getClientEmail();
        long totalAmount = paymentResult.getTotalAmount();

        long fee = (long) (totalAmount * vat);
        long paymentAmount = totalAmount - fee;
        //SettleStatus settleStatus = SettleStatus.DEPOSIT_SCHEDULED;
        String settleStatus = "0";


        DailySettle settle = DailySettle.createSettle(clientEmail, totalAmount, LocalDate.now(), settleStatus, fee,
                paymentAmount);
        log.info("settle : {}", settle);
        return settle;
    }
}