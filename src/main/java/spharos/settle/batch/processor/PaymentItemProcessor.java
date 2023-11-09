package spharos.settle.batch.processor;

import static java.util.stream.Collectors.summingInt;

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
import spharos.settle.dto.PaymentResultResponseList;

@Component
@Slf4j
public class PaymentItemProcessor implements ItemProcessor<PaymentResultResponseList, Map<String, Integer>> {

    @Override
    public Map<String, Integer> process(PaymentResultResponseList item) throws Exception {
        log.info("Processing item: {}", item);

        // 이름을 그룹화하고 합계를 계산하여 Map에 담음
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put(item.getClientEmail(), item.getTotalAmount());

        return resultMap;
    }
}