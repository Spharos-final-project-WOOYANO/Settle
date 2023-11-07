package spharos.settle.infrastructure.settle;

import org.springframework.data.jpa.repository.JpaRepository;
import spharos.settle.domain.settle.DailySettle;

public interface DailySettleRepository extends JpaRepository<DailySettle, Long> {
}
