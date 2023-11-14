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
public class SettleItemWriter implements ItemWriter<DailySettle> {
    private final DailySettleRepository dailySettleRepository;
    private static final double vat = 0.02;

    @Override
    public void write(Chunk<? extends DailySettle> chunk) throws Exception {
    log.info("chunk : {}", chunk);

    for(DailySettle dailySettle : chunk){
        dailySettleRepository.save(dailySettle);
    }
    }
}





