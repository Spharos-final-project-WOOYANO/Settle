package spharos.settle.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import spharos.settle.domain.settle.DailySettle;
import spharos.settle.infrastructure.DailySettleRepository;

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





