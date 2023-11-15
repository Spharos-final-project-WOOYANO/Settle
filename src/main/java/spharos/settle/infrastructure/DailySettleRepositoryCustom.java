package spharos.settle.infrastructure;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spharos.settle.dto.DailySettleListResponse;

public interface DailySettleRepositoryCustom {
    Page<DailySettleListResponse> findBySettlementDateBetween(String clientEmail, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
