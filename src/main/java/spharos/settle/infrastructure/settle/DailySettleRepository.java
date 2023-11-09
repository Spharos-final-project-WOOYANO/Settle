package spharos.settle.infrastructure.settle;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import spharos.settle.domain.settle.DailySettle;

public interface DailySettleRepository extends JpaRepository<DailySettle, Long> {
    Optional<DailySettle> findByClientEmail(String clientEmail);
}
