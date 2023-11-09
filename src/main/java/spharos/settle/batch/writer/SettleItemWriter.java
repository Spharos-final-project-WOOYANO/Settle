package spharos.settle.batch.writer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.domain.settle.SettleStatus;
import spharos.settle.dto.PaymentResult;
import spharos.settle.dto.PaymentResultResponseList;
import spharos.settle.infrastructure.settle.DailySettleRepository;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SettleItemWriter implements ItemWriter<PaymentResult> {
    private final DailySettleRepository dailySettleRepository;

    @Override
    public void write(Chunk<? extends PaymentResult> chunk) throws Exception {
    for(PaymentResult paymentResult : chunk){
        String clientEmail = paymentResult.getClientEmail();
        Long totalAmount = paymentResult.getTotalAmount();
        DailySettle settle1 = DailySettle.createSettle1(clientEmail, totalAmount, LocalDate.now());
        dailySettleRepository.save(settle1);

    }
    }
    }

   /* @Override
    public void write(Chunk<? extends PaymentResultResponseList> chunks) throws Exception {
        log.info("chunks size ");
        for (PaymentResultResponseList chunk : chunks) {
            String clientEmail = chunk.getClientEmail();
            int totalAmount = chunk.getTotalAmount();

            // DailySettle 엔터티에서 clientEmail에 해당하는 항목을 찾음
            Optional<DailySettle> existingSettle = dailySettleRepository.findByClientEmail(clientEmail);

            if (existingSettle.isPresent()) {
                    // 이미 해당 clientEmail에 대한 레코드가 있을 경우 금액을 더함
                DailySettle dailySettle = existingSettle.get();
                dailySettle.setTotalAmount(totalAmount);
                dailySettleRepository.save(dailySettle);
                } else {
                    // 해당 clientEmail에 대한 레코드가 없을 경우 새로운 DailySettle 엔터티를 생성하여 저장

                    DailySettle settle = DailySettle.createSettle1(clientEmail, totalAmount, LocalDate.now());

                    dailySettleRepository.save(settle);
                }
            }
        }*/






